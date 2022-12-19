package com.example.e_commerceapp.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_commerceapp.Models.ProductModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BasketFragmentViewModel : ViewModel() {

    private val db = Firebase.firestore
    val basketListLiveData = MutableLiveData<ArrayList<ProductModel>>()
    val basket_list = ArrayList<ProductModel>()

    fun getBasketList(){

        db.collection("userUID")
            .document("basket")
            .collection("productsInBasket")
            .get().addOnSuccessListener {

                for (i in it){
                    val doc = it.documents
                    for (i in doc){
                        val id = i.get("id") as Long
                        val title = i.get("title") as String
                        val price = i.get("price") as Double
                        val description = i.get("description") as String
                        val image = i.get("image") as String
                        val isSelected = i.get("selected") as Boolean
                        val isInBasket = i.get("inBasket") as Boolean
                        val product = ProductModel(id.toInt(),title,price.toFloat(),description,image,isSelected)
                        basket_list.add(product)
                    }
                    basketListLiveData.value = basket_list
                }

            }

    }

}