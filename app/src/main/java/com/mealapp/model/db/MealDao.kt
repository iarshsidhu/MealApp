package com.mealapp.model.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mealapp.model.model.pojo.Meal

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: Meal)

    @Delete
    suspend fun deleteMeal(meal: Meal)

    @Query("SELECT * FROM  mealInformation")
    fun getAllMeals(): LiveData<List<Meal>>

}