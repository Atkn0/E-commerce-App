package com.example.e_commerceapp.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_commerceapp.Models.ProductModel
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class ProductDetailViewModel : ViewModel() {

    private val db = Firebase.firestore
    val basketBoxCounter = MutableLiveData<Int>()



    suspend fun getBasketBoxCounter(): Int {
        var counter = 0
        db.collection("userUID")
            .document("basket")
            .collection("productsInBasket").get().addOnSuccessListener {
                for (document in it){
                    counter++
                }
            }.await()
        return counter
    }

    suspend fun addToBasket(product: ProductModel){
        product.isInBasket = true
        db.collection("userUID")
            .document("basket")
            .collection("productsInBasket")
            .document(product.id.toString())
            .set(product, SetOptions.merge()).await()
    }

    suspend fun removeFromBasket(product: ProductModel){
        product.isInBasket = false
        db.collection("userUID")
            .document("basket")
            .collection("productsInBasket")
            .document(product.id.toString())
            .delete().await()
    }

    suspend fun basketControl(product: ProductModel):Boolean{
        val document = db.collection("userUID")
            .document("basket")
            .collection("productsInBasket")
            .document(product.id.toString())
            .get().await()
        return document.exists()
    }


}





