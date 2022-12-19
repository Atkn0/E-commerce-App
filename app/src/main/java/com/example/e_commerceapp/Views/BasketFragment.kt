package com.example.e_commerceapp.Views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerceapp.Adapters.BasketRecyclerViewAdapter
import com.example.e_commerceapp.R
import com.example.e_commerceapp.ViewModels.BasketFragmentViewModel
import com.example.e_commerceapp.databinding.FragmentBasketBinding


class BasketFragment : Fragment() {

    lateinit var binding: FragmentBasketBinding
    private lateinit var basketViewModel: BasketFragmentViewModel
    lateinit var adapter: BasketRecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        basketViewModel = ViewModelProvider(this)[BasketFragmentViewModel::class.java]
        basketViewModel.getBasketList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBasketBinding.inflate(inflater)
        val view = binding.root

        adapter = BasketRecyclerViewAdapter(arrayListOf())
        binding.BasketRecyclerView.adapter = adapter
        binding.BasketRecyclerView.setHasFixedSize(true)
        binding.BasketRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        basketViewModel.basketListLiveData.observe(viewLifecycleOwner, Observer {
            adapter.updateBasketList(it)
        })

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)







    }


}











