package com.example.e_commerceapp.Views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.e_commerceapp.Adapters.FavoriteRecyclerViewAdapter
import com.example.e_commerceapp.Models.ProductModel
import com.example.e_commerceapp.R
import com.example.e_commerceapp.ViewModels.FavoritePageViewModel
import com.example.e_commerceapp.ViewModels.HomePageViewModel
import com.example.e_commerceapp.databinding.FragmentFavouritePageBinding
import kotlinx.coroutines.*


class FavouritePageFragment : Fragment() {

    lateinit var favoriteAdapter:FavoriteRecyclerViewAdapter
    lateinit var binding: FragmentFavouritePageBinding
    lateinit var favoriteViewModel:FavoritePageViewModel
    private var favoriteList = ArrayList<ProductModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteViewModel = ViewModelProvider(this)[FavoritePageViewModel::class.java]

        runBlocking(Dispatchers.IO) {
                favoriteList = favoriteViewModel.getAllFavoriteProducts()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavouritePageBinding.inflate(inflater)
        val view = binding.root

        favoriteAdapter = FavoriteRecyclerViewAdapter(favoriteList)
        binding.FavoriteRecyclerView.adapter = favoriteAdapter
        binding.FavoriteRecyclerView.setHasFixedSize(true)
        binding.FavoriteRecyclerView.layoutManager = GridLayoutManager(context,2)


        return view
    }


}