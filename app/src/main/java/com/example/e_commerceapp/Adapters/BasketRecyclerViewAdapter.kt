package com.example.e_commerceapp.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceapp.Models.ProductModel
import com.example.e_commerceapp.databinding.ProductBasketLayoutBinding

class BasketRecyclerViewAdapter(val basketList:ArrayList<ProductModel>):RecyclerView.Adapter<BasketRecyclerViewAdapter.BasketViewHolder>() {

    class BasketViewHolder(val binding: ProductBasketLayoutBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        val binding = ProductBasketLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BasketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        holder.binding.ProductBasketLayout
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