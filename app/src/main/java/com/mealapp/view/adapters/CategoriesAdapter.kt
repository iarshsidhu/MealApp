package com.mealapp.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mealapp.R
import com.mealapp.databinding.CategoryItemsBinding
import com.mealapp.model.model.pojo.Category
import java.util.*
import kotlin.collections.ArrayList

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    private var categoryList = ArrayList<Category>()
    var onItemClick: ((Category) -> Unit)? = null

    fun setCategoryList(category: List<Category>) {
        this.categoryList = category as ArrayList<Category>
        notifyDataSetChanged()
    }

    class CategoryViewHolder(var binding: CategoryItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(CategoryItemsBinding.inflate(LayoutInflater.from(parent.context)))
    }

    @SuppressLint("NewApi")
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        Glide.with(holder.itemView).load(categoryList[position].strCategoryThumb)
            .into(holder.binding.ivCategory)
        holder.binding.tvCategoryName.text = categoryList[position].strCategory

        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(categoryList[position])
        }

        holder.binding.cardView.setCardBackgroundColor(
            holder.itemView.resources.getColor(
                getRandomColor(),
                null
            )
        )

        holder.binding.categoryDetails.text = categoryList[position].strCategoryDescription
    }

    private fun getRandomColor(): Int {
        val color: ArrayList<Int> = ArrayList()
        color.add(R.color.color1)
        color.add(R.color.color2)
        color.add(R.color.color3)
        color.add(R.color.color4)
        color.add(R.color.color5)
        color.add(R.color.color6)
        color.add(R.color.color7)
        color.add(R.color.color8)
        color.add(R.color.color9)
        color.add(R.color.color10)
        color.add(R.color.color11)
        color.add(R.color.color12)
        color.add(R.color.color13)
        color.add(R.color.color14)
        color.add(R.color.color15)
        color.add(R.color.color16)

        val random = Random()
        val num: Int = random.nextInt(color.size)
        return color[num]
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}