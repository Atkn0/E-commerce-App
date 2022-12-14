package com.example.e_commerceapp.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceapp.Models.ProductModel
import com.example.e_commerceapp.databinding.FragmentFavouritePageBinding
import com.example.e_commerceapp.databinding.TestLayoutBinding
import com.squareup.picasso.Picasso

class FavoriteRecyclerViewAdapter(val favoriteList:ArrayList<ProductModel>):RecyclerView.Adapter<FavoriteRecyclerViewAdapter.favoriteViewHolder>() {

    var onFavoriteClick: ((ProductModel) -> Unit)? = null


    class favoriteViewHolder(val binding: TestLayoutBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): favoriteViewHolder {
        val binding = TestLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return favoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: favoriteViewHolder, position: Int) {

        val product = favoriteList[position]
        holder.binding.ProductLayout
        holder.binding.PriceTextView.text = product.price.toString()
        holder.binding.ProductNameTextView.text = product.title
        Picasso
            .get()
            .load(product.image)
            .into(holder.binding.ProductImageView)

        holder.binding.addToFavoriteIcon.setOnClickListener {
            product.isSelected = !product.isSelected
            notifyDataSetChanged()
            onFavoriteClick?.invoke(product)
        }

    }


    override fun getItemCount(): Int {
        return favoriteList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateFavoriteList(newFavoriteList:List<ProductModel>){
        favoriteList.clear()
        favoriteList.addAll(newFavoriteList)
        notifyDataSetChanged()
    }

}