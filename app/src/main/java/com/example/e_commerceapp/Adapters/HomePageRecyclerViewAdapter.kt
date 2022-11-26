package com.example.e_commerceapp.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceapp.databinding.FragmentHomePageBinding
import com.example.e_commerceapp.databinding.TestLayoutBinding

class HomePageRecyclerViewAdapter:RecyclerView.Adapter<HomePageRecyclerViewAdapter.myViewHolder>() {
    class myViewHolder(val binding: TestLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val binding = TestLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return myViewHolder(binding)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.binding.TestLayout
    }

    override fun getItemCount(): Int {
        return 15
    }
}