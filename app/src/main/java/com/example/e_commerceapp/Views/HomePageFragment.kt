package com.example.e_commerceapp.Views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerceapp.Adapters.HomePageRecyclerViewAdapter
import com.example.e_commerceapp.Models.ProductModel
import com.example.e_commerceapp.R
import com.example.e_commerceapp.ViewModels.HomePageViewModel
import com.example.e_commerceapp.databinding.FragmentHomePageBinding
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

class HomePageFragment : Fragment() {

    lateinit var binding:FragmentHomePageBinding
    private lateinit var viewModel:HomePageViewModel
    lateinit var adapter:HomePageRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[HomePageViewModel::class.java]
        viewModel.signInWithEmailPassword("arda@gmail.com","123456", requireContext())
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomePageBinding.inflate(inflater)
        val view = binding.root
        adapter = HomePageRecyclerViewAdapter(arrayListOf())
        var lastAdapterList = ArrayList<ProductModel>()


        GlobalScope.launch (Dispatchers.IO){
            println("AWAİT ÖNCESİNDE")
            async { viewModel.getAllProducts() }.await()
            println("AWAİT BİTTİKTEN SONRA")
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
                println("Filtered list boş")
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


        adapter.onItemClick = {
            val myModel = viewModel.addToCartFunc(it)

            viewModel.basketObserver()
        }

        viewModel.basketCounter.observe(viewLifecycleOwner, Observer {
            binding.ToolBarLayout.basketCounterTextView.text = it.toString()
        })




    }

}