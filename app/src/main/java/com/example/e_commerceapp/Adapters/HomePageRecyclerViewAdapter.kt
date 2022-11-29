package com.example.e_commerceapp.Adapters

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceapp.Models.ProductModel
import com.example.e_commerceapp.databinding.FragmentHomePageBinding
import com.example.e_commerceapp.databinding.TestLayoutBinding
import com.squareup.picasso.Picasso

class HomePageRecyclerViewAdapter(var list: ArrayList<ProductModel>):RecyclerView.Adapter<HomePageRecyclerViewAdapter.myViewHolder>() {
    class myViewHolder(val binding: TestLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    }


    fun filterList(filterList:ArrayList<ProductModel>){

        list = filterList
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val binding = TestLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return myViewHolder(binding)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.binding.ProductLayout
        holder.binding.PriceTextView.text = list[position].price.toString()
        holder.binding.ProductNameTextView.text = list[position].title
        Picasso
            .get()
            .load(list[position].image)
            .into(holder.binding.ProductImageView)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateAdapter(newList:ArrayList<ProductModel>){

        list.clear()
        list = newList
        notifyDataSetChanged()


    }

}