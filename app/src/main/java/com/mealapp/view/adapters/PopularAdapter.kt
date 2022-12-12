package com.mealapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mealapp.databinding.PopularItemsBinding
import com.mealapp.model.model.pojo.MealsByCategory

class PopularAdapter() : RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {
    lateinit var onItemClick: ((MealsByCategory) -> Unit)
    private var mealsList = ArrayList<MealsByCategory>()

    fun setMeals(mealList: ArrayList<MealsByCategory>) {
        this.mealsList = mealList
        notifyDataSetChanged()
    }

    class PopularViewHolder(var binding: PopularItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(
            PopularItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.ivPopularItems)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealsList[position])
        }
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }
}