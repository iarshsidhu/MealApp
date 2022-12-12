package com.mealapp.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.mealapp.view.activities.CategoryMealsActivity
import com.mealapp.view.activities.MealActivity
import com.mealapp.view.adapters.CategoriesAdapter
import com.mealapp.view.adapters.PopularAdapter
import com.mealapp.databinding.FragmentHomeBinding
import com.mealapp.model.db.MealDatabase
import com.mealapp.model.model.pojo.Category
import com.mealapp.model.model.pojo.MealsByCategory
import com.mealapp.model.model.pojo.Meal
import com.mealapp.util.KeyConstants
import com.mealapp.viewmodel.HomeViewModel
import com.mealapp.viewmodel.HomeViewModelFactory

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularAdapter: PopularAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val meadDatabase = MealDatabase.getInstance(requireContext())
        val viewModelFactory = HomeViewModelFactory(meadDatabase)
        homeMvvm = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemsRecyclerView()

        homeMvvm.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()

        homeMvvm.getPopularItems()
        observerPopularItems()
        onPopularItemClick()

        prepareCategoryRecyclerView()
        homeMvvm.getCategories()
        observerCategoryLiveData()
        onCategoryClick()
    }

    private fun prepareCategoryRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.recyclerViewCategories.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun observerCategoryLiveData() {
        homeMvvm.observeCategoryLiveData()
            .observe(viewLifecycleOwner, object : Observer<List<Category>> {
                override fun onChanged(t: List<Category>?) {
                    categoriesAdapter.setCategoryList(t!!)
                }
            })
    }

    private fun onPopularItemClick() {
        popularAdapter.onItemClick = { meal ->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(KeyConstants.MEAL_ID, meal.idMeal)
            intent.putExtra(KeyConstants.MEAL_NAME, meal.strMeal)
            intent.putExtra(KeyConstants.MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = { category ->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(KeyConstants.CATEGORY_NAME, category.strCategory)
            intent.putExtra(KeyConstants.CATEGORY_IMAGE, category.strCategoryThumb)
            intent.putExtra(KeyConstants.CATEGORY_CONTENT, category.strCategoryDescription)
            startActivity(intent)
        }
    }

    private fun preparePopularItemsRecyclerView() {
        popularAdapter = PopularAdapter()
        binding.recyclerViewPopularItems.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularAdapter
        }
    }

    private fun observerPopularItems() {
        homeMvvm.observePopularItemsLiveData()
            .observe(viewLifecycleOwner, object : Observer<List<MealsByCategory>> {
                override fun onChanged(t: List<MealsByCategory>?) {
                    popularAdapter.setMeals(mealList = t as ArrayList<MealsByCategory>)
                }
            })
    }

    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(KeyConstants.MEAL_ID, randomMeal.idMeal)
            intent.putExtra(KeyConstants.MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(KeyConstants.MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observerRandomMeal() {
        homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner, object : Observer<Meal> {
            override fun onChanged(meal: Meal?) {
                Glide.with(this@HomeFragment)
                    .load(meal!!.strMealThumb)
                    .into(binding.ivRandomMeal)

                this@HomeFragment.randomMeal = meal
            }
        })
    }

}