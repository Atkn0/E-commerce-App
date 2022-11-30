package com.example.e_commerceapp.ViewModels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_commerceapp.Models.ProductModel
import com.example.e_commerceapp.data.PhotoApiService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.UUID

class HomePageViewModel:ViewModel() {

    private var apiService = PhotoApiService()
    val productModelList = MutableLiveData<ArrayList<ProductModel>>()
    private lateinit var auth:FirebaseAuth
    var currentUserId: MutableLiveData<String>? = null
    val db = Firebase.firestore
    val basketCounter:MutableLiveData<Int> = MutableLiveData()




    suspend fun getAllProducts(){
        val productList = apiService.getApiFromService()
        productModelList.postValue(productList)
    }

    fun addToCartFunc(selectedModel:ProductModel):ProductModel{
        val newHashMap = HashMap<String,Any>()
        newHashMap.put("id",selectedModel.id)
        newHashMap.put("title",selectedModel.title)
        newHashMap.put("price",selectedModel.price)
        newHashMap.put("description",selectedModel.description)
        newHashMap.put("image",selectedModel.image)
        newHashMap.put("isSelected",selectedModel.isSelected)


        selectedModel.isSelected = !selectedModel.isSelected
        db.collection("userUID")
            .document("basket")
            .collection("productsInBasket")
            .add(newHashMap)


        return selectedModel

    }



    fun basketObserver(){
        db.collection("userUID")
            .document("basket")
            .collection("productsInBasket")
            .get().addOnCompleteListener {


                basketCounter.postValue(it.result.documents.size)

            }
    }

    fun signInWithEmailPassword(email:String,password:String,context: Context){

        auth = Firebase.auth
        val isUserLogged = auth.currentUser
        if (isUserLogged != null){
            println("User already logged in.")
            currentUserId?.value = auth.currentUser?.uid
        }else{

            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    currentUserId?.value = auth.currentUser?.uid
                    Toast.makeText(context, "User login successful", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context, "User login is not successful", Toast.LENGTH_SHORT).show()
                }

            }

        }


    }

}