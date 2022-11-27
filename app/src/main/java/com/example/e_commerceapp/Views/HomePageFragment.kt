package com.example.e_commerceapp.Views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerceapp.Adapters.HomePageRecyclerViewAdapter
import com.example.e_commerceapp.R
import com.example.e_commerceapp.databinding.FragmentHomePageBinding

class HomePageFragment : Fragment() {

    lateinit var binding:FragmentHomePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomePageBinding.inflate(inflater)
        val view = binding.root
        val adapter = HomePageRecyclerViewAdapter()

        binding.ReycylerView.adapter = adapter
        binding.ReycylerView.setHasFixedSize(true)
        binding.ReycylerView.layoutManager = GridLayoutManager(context,2)


        return view
    }
}