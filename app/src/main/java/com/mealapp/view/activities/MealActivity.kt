package com.mealapp.view.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.mealapp.R
import com.mealapp.databinding.ActivityMealBinding
import com.mealapp.model.db.MealDatabase
import com.mealapp.model.model.pojo.Meal
import com.mealapp.util.KeyConstants
import com.mealapp.viewmodel.MealViewModel
import com.mealapp.viewmodel.MealViewModelFactory

class MealActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealBinding
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var youtubeLink: String
    private lateinit var mealMvvm: MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val meadDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(meadDatabase)
        mealMvvm = ViewModelProvider(this, viewModelFactory)[MealViewModel::class.java]

        getMealInformation()

        setInformation()

        loading()
        mealMvvm.getMealDetail(mealId)
        observerMealDetailsLiveData()

        onYoutubeClick()
        onFavoriteClick()
    }

    private fun onFavoriteClick() {
        binding.addToFav.setOnClickListener {
            mealToSave?.let {
                mealMvvm.insertMeal(it)
                Toast.makeText(this, "Meal Saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onYoutubeClick() {
        binding.ivYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private fun getMealInformation() {
        val intent = intent
        mealId = intent.getStringExtra(KeyConstants.MEAL_ID)!!
        mealName = intent.getStringExtra(KeyConstants.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(KeyConstants.MEAL_THUMB)!!
    }

    private fun setInformation() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imageDetailMeal)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private var mealToSave: Meal? = null
    private fun observerMealDetailsLiveData() {
        mealMvvm.observeMealDetailsLiveData().observe(this, object : Observer<Meal> {
            override fun onChanged(meal: Meal?) {
                onResponse()
                mealToSave = meal
                binding.tvCategory.text = "Category : ${meal!!.strCategory}"
                binding.tvArea.text = "Area : ${meal.strArea}"
                binding.tvInstruct.text = meal.strInstructions
                youtubeLink = meal.strYoutube!!
            }
        })
    }

    private fun loading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.addToFav.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.ivYoutube.visibility = View.INVISIBLE
    }

    private fun onResponse() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.addToFav.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.ivYoutube.visibility = View.VISIBLE
    }
}