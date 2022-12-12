package com.mealapp.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.mealapp.view.adapters.FavoriteAdapter
import com.mealapp.databinding.FragmentFavouriteBinding
import com.mealapp.model.db.MealDatabase
import com.mealapp.viewmodel.HomeViewModel
import com.mealapp.viewmodel.HomeViewModelFactory

class FavouriteFragment : Fragment() {
    private lateinit var binding: FragmentFavouriteBinding
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var favoriteAdapter: FavoriteAdapter

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
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observerFavorites()

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                homeMvvm.deleteMeal(favoriteAdapter.differ.currentList[position])

                Toast.makeText(requireContext(), "Meal deleted", Toast.LENGTH_SHORT).show()
            }
        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.favoriteRecyclerView)
    }

    private fun prepareRecyclerView() {
        favoriteAdapter = FavoriteAdapter()
        binding.favoriteRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = favoriteAdapter
        }
    }

    private fun observerFavorites() {
        homeMvvm.observeFavoritesMealLiveData().observe(viewLifecycleOwner, Observer { meals ->
            meals.forEach {
                favoriteAdapter.differ.submitList(meals)
            }
        })
    }
}