package com.example.e_commerceapp.data

import com.example.e_commerceapp.Models.ProductModel
import retrofit2.http.GET


interface ProductApi {

    //base url : https://fakestoreapi.com/

    @GET("products")
    suspend fun getProducts(): List<ProductModel>

}