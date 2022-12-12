package com.example.e_commerceapp.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceapp.Models.ProductModel
import com.example.e_commerceapp.databinding.FragmentFavouritePageBinding
import com.example.e_commerceapp.databinding.TestLayoutBinding
import com.squareup.picasso.Picasso

class FavoriteRecyclerViewAdapter(val favoriteList:ArrayList<ProductModel>):RecyclerView.Adapter<FavoriteRecyclerViewAdapter.favoriteViewHolder>() {
    class favoriteViewHolder(val binding: TestLayoutBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): favoriteViewHolder {
        val binding = TestLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return favoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: favoriteViewHolder, position: Int) {
        holder.binding.ProductLayout
    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }
}