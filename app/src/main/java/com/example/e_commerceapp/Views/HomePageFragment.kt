package com.example.e_commerceapp.Views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.e_commerceapp.Adapters.HomePageRecyclerViewAdapter
import com.example.e_commerceapp.Models.ProductModel
import com.example.e_commerceapp.ViewModels.HomePageViewModel
import com.example.e_commerceapp.ViewModels.MainActivityViewModel
import com.example.e_commerceapp.ViewModels.ProductDetailViewModel
import com.example.e_commerceapp.databinding.FragmentHomePageBinding
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

class HomePageFragment : Fragment() {

    lateinit var binding:FragmentHomePageBinding
    private lateinit var viewModel:HomePageViewModel
    private lateinit var productDetailViewModel: ProductDetailViewModel
    lateinit var adapter:HomePageRecyclerViewAdapter
    lateinit var mainViewModel:MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[HomePageViewModel::class.java]
        mainViewModel = ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]
        productDetailViewModel = ViewModelProvider(this)[ProductDetailViewModel::class.java]
        viewModel.signInWithEmailPassword("arda@gmail.com","123456",requireContext())
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomePageBinding.inflate(inflater)
        val view = binding.root

        adapter = HomePageRecyclerViewAdapter(arrayListOf(), requireContext())
        var lastAdapterList = ArrayList<ProductModel>()


        GlobalScope.launch (Dispatchers.Main){
            viewModel.getAllProducts()
        }

        binding.ReycylerView.adapter = adapter
        binding.ReycylerView.setHasFixedSize(true)
        binding.ReycylerView.layoutManager = GridLayoutManager(context,2)

        viewModel.productModelList.observe(viewLifecycleOwner, Observer {
            adapter.updateAdapter(it)
            lastAdapterList = it
        })



        //filter function
        fun filter(text:String){
            val filteredList:ArrayList<ProductModel> = ArrayList<ProductModel>()

            for (item in lastAdapterList){
                if(item.title.lowercase().contains(text.lowercase(Locale.getDefault()))){
                    filteredList.add(item)
                }
            }

            if (filteredList.isEmpty()){
            }else{
                adapter.filterList(filteredList)
            }

        }

        binding.HomePageSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener  {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText!!)
                return false
            }

        })
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.toolbarBasketCounter.observe(viewLifecycleOwner, Observer {
            binding.ToolBarLayout.basketCounterTextView.text = it.toString()
        })

        adapter.onFavoriteIconClick = {
            lifecycleScope.launch {
                viewModel.controlFavoriteDatabase(it)
            }
        }

        adapter.onProductClick = {
            val action = HomePageFragmentDirections.actionHomePageFragmentToProductDetailFragment(it)
            findNavController().navigate(action)
        }

    }

}