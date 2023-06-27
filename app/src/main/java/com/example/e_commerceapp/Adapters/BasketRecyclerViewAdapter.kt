package com.example.e_commerceapp.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceapp.Models.ProductModel
import com.example.e_commerceapp.databinding.ProductBasketLayoutBinding
import com.squareup.picasso.Picasso

class BasketRecyclerViewAdapter(private val basketList:ArrayList<ProductModel>):RecyclerView.Adapter<BasketRecyclerViewAdapter.BasketViewHolder>() {

    class BasketViewHolder(val binding: ProductBasketLayoutBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        val binding = ProductBasketLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BasketViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        val product = basketList[position]

        if (product.title.length > 20){
            holder.binding.BasketProductNameTextView.text = product.title.substring(0,15) + "..."
        }else{
            holder.binding.BasketProductNameTextView.text = product.title
        }
        holder.binding.ProductPriceBaskettextView.text = product.price.toString()
        Picasso
            .get()
            .load(product.image)
            .into(holder.binding.BasketProductImageView)

    }

    override fun getItemCount(): Int {
        return basketList.size
    }

    fun updateBasketList(newBasketList:ArrayList<ProductModel>){
        basketList.clear()
        basketList.addAll(newBasketList)
        notifyDataSetChanged()
    }


}