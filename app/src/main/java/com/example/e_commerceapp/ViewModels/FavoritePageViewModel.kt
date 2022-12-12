package com.example.e_commerceapp.ViewModels

import androidx.lifecycle.ViewModel
import com.example.e_commerceapp.Models.ProductModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FavoritePageViewModel:ViewModel() {

    private val db = Firebase.firestore

    suspend fun getAllFavoriteProducts():ArrayList<ProductModel>{

        val list  = ArrayList<ProductModel>()
        db.collection("userUID")
            .document("favorite")
            .collection("productsInFavorite")
            .get()
            .addOnSuccessListener {
                val doc = it.documents
                for (i in doc){

                    //BUG: Review this code (it's about ProductModel)
                    val newProductModel = ProductModel(
                        i.get("id") as Int,
                        i.get("title") as String,
                        i.get("price") as Float,
                        i.get("description") as String,
                        i.get("image") as String,
                        i.get("isSelected") as Boolean
                    )
                    list.add(newProductModel)
                }

            }.await()

        return list

    }

}