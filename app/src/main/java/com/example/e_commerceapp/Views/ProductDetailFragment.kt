package com.example.e_commerceapp.Views

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.provider.CalendarContract.Colors
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.e_commerceapp.Models.ProductModel
import com.example.e_commerceapp.R
import com.example.e_commerceapp.ViewModels.HomePageViewModel
import com.example.e_commerceapp.ViewModels.MainActivityViewModel
import com.example.e_commerceapp.ViewModels.ProductDetailViewModel
import com.example.e_commerceapp.databinding.FragmentProductDetailBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class ProductDetailFragment : Fragment() {

    private val args by navArgs<ProductDetailFragmentArgs>()
    lateinit var binding: FragmentProductDetailBinding
    private lateinit var Product:ProductModel
    lateinit var productViewModel: ProductDetailViewModel
    lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Product = args.productDetail
        productViewModel = ViewModelProvider(this)[ProductDetailViewModel::class.java]
        mainActivityViewModel = ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]
        runBlocking {
            println("scope içinde")
            val boolTest = productViewModel.basketControl(Product)
            println("after await boolTest = $boolTest")
            Product.isInBasket = boolTest
            println("scope bitiyor")
        }
        println("scope dışında")


    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        println("onCreate içinde ve booltest = " + Product.isInBasket)

        if (Product.isInBasket){
            binding.ProductDetailAddToCartButton.text = "Already In Basket"
            binding.ProductDetailAddToCartButton.setBackgroundColor(Color.GREEN)
        }else{
            binding.ProductDetailAddToCartButton.text = "Add To Basket"
            binding.ProductDetailAddToCartButton.setBackgroundColor(Color.RED)
        }

        Picasso.get().load(args.productDetail.image).into(binding.ProductDetailImageView)
        binding.ProductDetailDescriptionTextView.text = args.productDetail.description
        binding.ProductDetailPriceTextView.text = args.productDetail.price.toString()
        binding.ProductDetailNameTextView.text = args.productDetail.title

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ProductDetailAddToCartButton.setOnClickListener {
            lifecycleScope.launch {
                if (Product.isInBasket){
                    productViewModel.removeFromBasket(Product)
                    val count = productViewModel.getBasketBoxCounter()
                    mainActivityViewModel.toolbarBasketCounter.postValue(count)
                    binding.ProductDetailAddToCartButton.text = "Add To Basket"
                    binding.ProductDetailAddToCartButton.setBackgroundColor(Color.RED)
                }  else{
                    productViewModel.addToBasket(Product)
                    val count = productViewModel.getBasketBoxCounter()
                    mainActivityViewModel.toolbarBasketCounter.postValue(count)
                    binding.ProductDetailAddToCartButton.text = "Remove From Basket"
                    binding.ProductDetailAddToCartButton.setBackgroundColor(Color.GREEN)
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
    }

}