package com.example.e_commerceapp.Views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerceapp.Adapters.HomePageRecyclerViewAdapter
import com.example.e_commerceapp.R
import com.example.e_commerceapp.ViewModels.HomePageViewModel
import com.example.e_commerceapp.databinding.FragmentHomePageBinding
import kotlinx.coroutines.*

class HomePageFragment : Fragment() {

    lateinit var binding:FragmentHomePageBinding
    private lateinit var viewModel:HomePageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomePageBinding.inflate(inflater)
        val view = binding.root
        val adapter = HomePageRecyclerViewAdapter(arrayListOf())

        viewModel = ViewModelProvider(this)[HomePageViewModel::class.java]

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

        })

        return view
    }
}