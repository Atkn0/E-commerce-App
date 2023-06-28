package com.example.e_commerceapp.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceapp.Models.ProductModel
import com.example.e_commerceapp.ViewModels.BasketFragmentViewModel
import com.example.e_commerceapp.databinding.ProductBasketLayoutBinding
import com.squareup.picasso.Picasso

class BasketRecyclerViewAdapter(private val basketList:ArrayList<ProductModel>,private val basketFragmentViewModel: BasketFragmentViewModel):RecyclerView.Adapter<BasketRecyclerViewAdapter.BasketViewHolder>() {



    var onProductDeleteClick: ((ProductModel) -> Unit)? = null


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

        holder.binding.BasketProductDeleteButton.setOnClickListener {
           // product.isInBasket = !product.isInBasket
            onProductDeleteClick?.invoke(product)
        }

        holder.binding.plusButton.setOnClickListener {
            var count = holder.binding.ProductCountTextView.text.toString().toInt()
            count += 1
            holder.binding.ProductCountTextView.text = count.toString()
            val newProductPrice = product.price * count
            holder.binding.ProductPriceBaskettextView.text = newProductPrice.toString()
            basketFragmentViewModel.totalPriceLiveData.value = basketFragmentViewModel.totalPriceLiveData.value?.plus(
                product.price
            )
        }

        holder.binding.minusButton.setOnClickListener {
            var count = holder.binding.ProductCountTextView.text.toString().toInt()
            if (count > 1){
                count -= 1
                holder.binding.ProductCountTextView.text = count.toString()
                val newProductPrice = product.price * count
                holder.binding.ProductPriceBaskettextView.text = newProductPrice.toString()
                basketFragmentViewModel.totalPriceLiveData.value = basketFragmentViewModel.totalPriceLiveData.value?.minus(
                    product.price
                )

            }
        }


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