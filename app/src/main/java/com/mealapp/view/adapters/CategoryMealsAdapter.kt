package com.mealapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mealapp.R
import com.mealapp.databinding.MealItemBinding
import com.mealapp.model.model.pojo.MealsByCategory

class CategoryMealsAdapter : RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewHolder>() {
    lateinit var onItemClick: ((MealsByCategory) -> Unit)
    private var mealsList = ArrayList<MealsByCategory>()

    fun setMealsList(mealsList: List<MealsByCategory>) {
        this.mealsList = mealsList as ArrayList<MealsByCategory>
        notifyDataSetChanged()
    }

    class CategoryMealsViewHolder(var binding: MealItemBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewHolder {
        return CategoryMealsViewHolder(MealItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CategoryMealsViewHolder, position: Int) {
        Glide.with(holder.itemView).load(mealsList[position].strMealThumb)
            .into(holder.binding.ivMeal)
        holder.binding.tvMealName.text = mealsList[position].strMeal
        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealsList[position])
        }
        holder.binding.cardView.startAnimation(
            AnimationUtils.loadAnimation(
                holder.itemView.context,
                R.anim.items_animation
            )
        )
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }
}