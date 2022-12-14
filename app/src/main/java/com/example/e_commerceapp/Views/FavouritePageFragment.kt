package com.example.e_commerceapp.Views

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
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


    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteViewModel = ViewModelProvider(this)[FavoritePageViewModel::class.java]
        println("onCreate içerisinde")

        GlobalScope.launch (Dispatchers.IO){
            favoriteViewModel.getAllFavoriteProducts()
            println(favoriteList)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavouritePageBinding.inflate(inflater)
        val view = binding.root

        favoriteAdapter = FavoriteRecyclerViewAdapter(favoriteList,requireContext())
        binding.FavoriteRecyclerView.adapter = favoriteAdapter
        binding.FavoriteRecyclerView.setHasFixedSize(true)
        binding.FavoriteRecyclerView.layoutManager = GridLayoutManager(context,2)


        return view
    }


    @SuppressLint("NotifyDataSetChanged")
    @DelicateCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        favoriteViewModel.allFavoriteLiveData.observe(viewLifecycleOwner, Observer{ newList ->

            if (newList.isEmpty()){
                binding.lottieAnimationView.visibility = View.VISIBLE
                binding.lottieTextView.visibility = View.VISIBLE
                binding.lottieAnimationView.playAnimation()
            }else{
                binding.lottieAnimationView.visibility = View.GONE
                binding.lottieTextView.visibility = View.GONE
                binding.lottieAnimationView.pauseAnimation()
            }
            favoriteAdapter.updateFavoriteList(newList)
        })


        favoriteAdapter.onFavoriteClick = { model ->
            runBlocking {
                val text = async { favoriteViewModel.removeFromFavorite(model) }.await()
                println("scope içinde")
                println(text)
            }
            println("scope dışında")
        }





    }


}