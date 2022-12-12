package com.example.e_commerceapp.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceapp.Models.ProductModel
import com.example.e_commerceapp.R
import com.example.e_commerceapp.databinding.FragmentHomePageBinding
import com.example.e_commerceapp.databinding.TestLayoutBinding
import com.squareup.picasso.Picasso
import kotlin.coroutines.coroutineContext

class HomePageRecyclerViewAdapter(var list: ArrayList<ProductModel>,val context: Context):RecyclerView.Adapter<HomePageRecyclerViewAdapter.myViewHolder>() {

    var onItemClick: ((ProductModel) -> Unit)? = null

    class myViewHolder(val binding: TestLayoutBinding) : RecyclerView.ViewHolder(binding.root) {}

    fun filterList(filterList: ArrayList<ProductModel>) {
        list = filterList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val binding = TestLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return myViewHolder(binding)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {

        val product = list[position]

        holder.binding.ProductLayout
        holder.binding.PriceTextView.text = product.price.toString()
        holder.binding.ProductNameTextView.text = product.title
        Picasso
            .get()
            .load(product.image)
            .into(holder.binding.ProductImageView)

        if (product.isSelected) {
            holder.binding.addToFavoriteIcon.setBackgroundColor(Color.RED)
        } else {
            holder.binding.addToFavoriteIcon.setBackgroundColor(Color.BLACK)
        }

        holder.binding.addToFavoriteIcon.setOnClickListener {
            product.isSelected = !product.isSelected
            notifyDataSetChanged()
            onItemClick?.invoke(product)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateAdapter(newList: ArrayList<ProductModel>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

}