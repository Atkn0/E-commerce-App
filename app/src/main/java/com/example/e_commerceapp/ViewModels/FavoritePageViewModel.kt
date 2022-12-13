package com.example.e_commerceapp.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.Models.ProductModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class FavoritePageViewModel:ViewModel() {

    private val db = Firebase.firestore
    val allFavoriteLiveData = MutableLiveData<ArrayList<ProductModel>>()

    suspend fun getAllFavoriteProducts(){

        val docList = ArrayList<ProductModel>()
        println("runBlocking öncesi")

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

    fun removeFromFavorite(selectedModel:ProductModel){
        db.collection("userUID")
            .document("favorite")
            .collection("productsInFavorite").document(selectedModel.id.toString()).delete()
            .addOnCompleteListener { task->
                if (task.isSuccessful){
                    println("Document deleted")
                }
            }
    }

}