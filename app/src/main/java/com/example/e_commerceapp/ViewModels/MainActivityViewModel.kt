package com.example.e_commerceapp.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivityViewModel : ViewModel() {

    val toolbarBasketCounter = MutableLiveData<Int>()
    private val db = Firebase.firestore


    fun getFirstBasketCount(){

        db.collection("userUID")
            .document("basket")
            .collection("productsInBasket").get().addOnSuccessListener {
                toolbarBasketCounter.postValue(it.size())
            }

    }


}