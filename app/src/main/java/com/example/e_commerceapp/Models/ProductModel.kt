package com.example.e_commerceapp.Models

data class ProductModel(
    val id:Int,
    val title:String,
    val price: Float,
    val description:String,
    val image:String,
    var isSelected:Boolean = false,
        )