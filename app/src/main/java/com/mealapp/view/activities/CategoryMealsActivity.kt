package com.mealapp.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.mealapp.view.adapters.CategoryMealsAdapter
import com.mealapp.databinding.ActivityCategoryMealsBinding
import com.mealapp.util.KeyConstants
import com.mealapp.viewmodel.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryMealsBinding
    private lateinit var categoryMealsViewModel: CategoryMealsViewModel
    private lateinit var categoryMealsAdapter: CategoryMealsAdapter
    private lateinit var categoryName: String
    private lateinit var categoryImage: String
    private lateinit var categoryContent: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getCategoryInformation()

        setCategoryInformation()

        prepareRecyclerView()

        categoryMealsViewModel = ViewModelProviders.of(this)[CategoryMealsViewModel::class.java]

        categoryMealsViewModel.getMealsByCategory(intent.getStringExtra(KeyConstants.CATEGORY_NAME)!!)

        categoryMealsViewModel.observeMealsLiveData().observe(this, Observer { mealsList ->
            mealsList.forEach {
                binding.categoryCount.text = "Items : " + mealsList.size.toString()
                categoryMealsAdapter.setMealsList(mealsList)
            }
        })

        onCategoriesClick()
    }

    private fun onCategoriesClick() {
        categoryMealsAdapter.onItemClick = { meal ->
            val intent = Intent(applicationContext, MealActivity::class.java)
            intent.putExtra(KeyConstants.MEAL_ID, meal.idMeal)
            intent.putExtra(KeyConstants.MEAL_NAME, meal.strMeal)
            intent.putExtra(KeyConstants.MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun prepareRecyclerView() {
        categoryMealsAdapter = CategoryMealsAdapter()
        binding.recyclerViewMeals.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = categoryMealsAdapter
        }
    }

    private fun getCategoryInformation() {
        val intent = intent
        categoryName = intent.getStringExtra(KeyConstants.CATEGORY_NAME)!!
        categoryImage = intent.getStringExtra(KeyConstants.CATEGORY_IMAGE)!!
        categoryContent = intent.getStringExtra(KeyConstants.CATEGORY_CONTENT)!!
    }

    private fun setCategoryInformation() {
        Glide.with(applicationContext)
            .load(categoryImage)
            .into(binding.ivCategory)

        binding.categoryName.text = categoryName
        binding.categoryContent.text = categoryContent
        binding.categoryContent.movementMethod = ScrollingMovementMethod()
    }
}