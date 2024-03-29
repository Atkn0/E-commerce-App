package com.example.e_commerceapp.ViewModels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.Models.ProductModel
import com.example.e_commerceapp.data.PhotoApiService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class HomePageViewModel:ViewModel() {

    private var apiService = PhotoApiService()
    val productModelList = MutableLiveData<ArrayList<ProductModel>>()
    private lateinit var auth:FirebaseAuth
    var currentUserId: MutableLiveData<String>? = null
    private val db = Firebase.firestore
    val homeBasketCounter = MutableLiveData<Int>()



    suspend fun getAllProducts(){
        val productList = apiService.getApiFromService()

        viewModelScope.launch {
            val list = async { getFavoriteFirestore() }.await()
            for (i in productList){
                for (j in list){
                    if (i.id == j.id){
                        i.isSelected = true
                    }
                }
            }
            productModelList.postValue(productList)
        }
    }



    suspend fun getFavoriteFirestore():ArrayList<ProductModel>{

        val firestoreFavoriteList = ArrayList<ProductModel>()
        db.collection("userUID")
            .document("favorite")
            .collection("productsInFavorite").get().addOnSuccessListener {
                for (document in it){
                    val product = ProductModel(document.get("id").toString().toInt(),
                        document.get("title") as String,
                        document.get("price").toString().toFloat(),
                        document.get("description") as String,
                        document.get("image") as String,
                        document.get("isSelected") as Boolean)
                    firestoreFavoriteList.add(product)
                }
            }.await()
        return firestoreFavoriteList
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

    fun addToFavorite(selectedModel:ProductModel){
        val newHashMap = HashMap<String,Any>()
        newHashMap.put("id",selectedModel.id)
        newHashMap.put("title",selectedModel.title)
        newHashMap.put("price",selectedModel.price)
        newHashMap.put("description",selectedModel.description)
        newHashMap.put("image",selectedModel.image)
        newHashMap.put("isSelected",selectedModel.isSelected)

        db.collection("userUID")
            .document("favorite")
            .collection("productsInFavorite").document(selectedModel.id.toString()).set(newHashMap,
                SetOptions.merge())
    }

    fun removeFromFavorite(selectedModel:ProductModel){
        db.collection("userUID")
            .document("favorite")
            .collection("productsInFavorite").document(selectedModel.id.toString()).delete()
    }


    suspend fun controlFavoriteDatabase(selectedModel: ProductModel) {
        //check if selectedModel is in favorite database
        val favoriteList = db.collection("userUID")
            .document("favorite")
            .collection("productsInFavorite").document(selectedModel.id.toString()).get().await()
        val product = favoriteList.get("isSelected")

        if (product == true) {
            removeFromFavorite(selectedModel)
        } else {
            addToFavorite(selectedModel)
        }

    }

}