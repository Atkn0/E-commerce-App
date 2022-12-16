package com.example.e_commerceapp.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceapp.Models.ProductModel
import com.example.e_commerceapp.R
import com.example.e_commerceapp.databinding.FragmentFavouritePageBinding
import com.example.e_commerceapp.databinding.TestLayoutBinding
import com.squareup.picasso.Picasso



class FavoriteRecyclerViewAdapter(val favoriteList:ArrayList<ProductModel>,val viewContext: Context):RecyclerView.Adapter<FavoriteRecyclerViewAdapter.favoriteViewHolder>() {

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

        if (product.isSelected) {
            holder.binding.addToFavoriteIcon.setImageDrawable(ContextCompat.getDrawable(viewContext, R.drawable.selected_favorite_icon))
        } else {
            holder.binding.addToFavoriteIcon.setImageDrawable(ContextCompat.getDrawable(viewContext, R.drawable.favorite_icon))
        }

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