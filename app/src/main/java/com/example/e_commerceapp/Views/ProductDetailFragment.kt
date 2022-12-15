package com.example.e_commerceapp.Views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.example.e_commerceapp.R
import com.example.e_commerceapp.databinding.FragmentProductDetailBinding
import com.squareup.picasso.Picasso


class ProductDetailFragment : Fragment() {

    private val args by navArgs<ProductDetailFragmentArgs>()
    lateinit var binding: FragmentProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        println("ProductDetailFragment args")
        println(args.productDetail)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)

        Picasso.get().load(args.productDetail.image).into(binding.ProductDetailImageView)
        binding.ProductDetailDescriptionTextView.text = args.productDetail.description
        binding.ProductDetailPriceTextView.text = args.productDetail.price.toString()
        binding.ProductDetailNameTextView.text = args.productDetail.title

        return binding.root
    }


}