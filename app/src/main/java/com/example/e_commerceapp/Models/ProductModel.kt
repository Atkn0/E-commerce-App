package com.example.e_commerceapp.Models


import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ProductModel(
    val id:Int,
    val title:String,
    var price: Float,
    val description:String,
    val image:String,
    var isSelected:Boolean = false,
    var isInBasket:Boolean = false,
) : Parcelable