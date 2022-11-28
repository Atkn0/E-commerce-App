package com.example.e_commerceapp.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_commerceapp.Models.ProductModel
import com.example.e_commerceapp.data.PhotoApiService

class HomePageViewModel:ViewModel() {

    private var apiService = PhotoApiService()
    val productModelList = MutableLiveData<ArrayList<ProductModel>>()


    suspend fun getAllProducts(){
        val productList = apiService.getApiFromService()
        productModelList.postValue(productList)

    }

}