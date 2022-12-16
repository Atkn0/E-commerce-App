package com.example.e_commerceapp.ViewModels

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceapp.Adapters.FavoriteRecyclerViewAdapter
import com.example.e_commerceapp.Models.ProductModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class FavoritePageViewModel:ViewModel() {

    private val db = Firebase.firestore
    val allFavoriteLiveData = MutableLiveData<ArrayList<ProductModel>>()

    suspend fun getAllFavoriteProducts(){

        val docList = ArrayList<ProductModel>()

        db.collection("userUID")
            .document("favorite")
            .collection("productsInFavorite")
            .get()
            .addOnSuccessListener {
                val doc = it.documents
                for (i in doc){
                    val id = i.get("id") as Long
                    val title = i.get("title") as String
                    val price = i.get("price") as Double
                    val description = i.get("description") as String
                    val image = i.get("image") as String
                    val isSelected = i.get("isSelected") as Boolean
                    val product = ProductModel(id.toInt(),title,price.toFloat(),description,image,isSelected)
                    docList.add(product)
                }
                allFavoriteLiveData.value = docList
            }
    }

    suspend fun removeFromFavorite(selectedModel:ProductModel):String{
        db.collection("userUID")
            .document("favorite")
            .collection("productsInFavorite").document(selectedModel.id.toString()).delete()
            .addOnCompleteListener {
                if (it.isSuccessful){
                    viewModelScope.launch {
                        getAllFavoriteProducts()
                    }
                }
            }
            .await()
            return "success"
    }


}