package com.example.e_commerceapp.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_commerceapp.Models.ProductModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class BasketFragmentViewModel : ViewModel() {

    private val db = Firebase.firestore
    val basketListLiveData = MutableLiveData<ArrayList<ProductModel>>()
    val totalPriceLiveData = MutableLiveData<Float>()
    private val basket_list = ArrayList<ProductModel>()


    fun getBasketList(){

        db.collection("userUID")
            .document("basket")
            .collection("productsInBasket")
            .get().addOnSuccessListener {

                val documentList = it.documents
                for (i in documentList){
                    val id = i.get("id") as Long
                    val title = i.get("title") as String
                    val price = i.get("price") as Double
                    val description = i.get("description") as String
                    val image = i.get("image") as String
                    val isSelected = i.get("selected") as Boolean
                    val isInBasket = i.get("inBasket") as Boolean
                    val product = ProductModel(id.toInt(),title,price.toFloat(),description,image,isSelected,isInBasket)
                    basket_list.add(product)
                }
                calculateTotalPrice()
                basketListLiveData.postValue(basket_list)
            }

    }

    fun removeFromBasket(product: ProductModel){
        product.isInBasket = false
        db.collection("userUID")
            .document("basket")
            .collection("productsInBasket")
            .document(product.id.toString())
            .delete().addOnSuccessListener {
                basket_list.remove(product)
                calculateTotalPrice()
                basketListLiveData.postValue(basket_list)
            }
     }


    fun calculateTotalPrice(){
        var totalPrice = 0f
        for (i in basket_list){
            totalPrice += i.price
        }
        println("postlanmadan önce total price: $totalPrice")
        totalPriceLiveData.postValue(totalPrice)
    }

    fun buyNow(){
        for (i in basket_list){
            i.isInBasket = false
            db.collection("userUID")
                .document("basket")
                .collection("productsInBasket")
                .document(i.id.toString())
                .delete()
        }
        basket_list.clear()
        totalPriceLiveData.postValue(0f)

        basketListLiveData.postValue(basket_list)


    }

}
