package com.example.e_commerceapp.data

import com.example.e_commerceapp.Models.ProductModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class PhotoApiService {

    val BASE_URL:String = "https://fakestoreapi.com/"

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(ProductApi::class.java)

    suspend fun getApiFromService () : List<ProductModel>{
        return api.getProducts()
    }

}