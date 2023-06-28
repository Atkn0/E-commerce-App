package com.example.e_commerceapp.Views

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerceapp.Adapters.BasketRecyclerViewAdapter
import com.example.e_commerceapp.R
import com.example.e_commerceapp.ViewModels.BasketFragmentViewModel
import com.example.e_commerceapp.ViewModels.MainActivityViewModel
import com.example.e_commerceapp.databinding.FragmentBasketBinding
import kotlin.properties.Delegates


class BasketFragment : Fragment() {

    lateinit var binding: FragmentBasketBinding
    private lateinit var basketViewModel: BasketFragmentViewModel
    private lateinit var mainActivityViewModel: MainActivityViewModel
    lateinit var adapter: BasketRecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        basketViewModel = ViewModelProvider(this)[BasketFragmentViewModel::class.java]
        mainActivityViewModel = ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]
        basketViewModel.getBasketList()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBasketBinding.inflate(inflater)
        val view = binding.root


        binding.emptyBasketAnimation.playAnimation()

        adapter = BasketRecyclerViewAdapter(arrayListOf())
        binding.BasketRecyclerView.adapter = adapter
        binding.BasketRecyclerView.setHasFixedSize(true)
        binding.BasketRecyclerView.layoutManager = LinearLayoutManager(context)


        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        basketViewModel.basketListLiveData.observe(viewLifecycleOwner, Observer {

            if(it.isEmpty()){
                binding.emptyBasketAnimation.visibility = View.VISIBLE
                binding.emptyBasketTextView.visibility = View.VISIBLE
                binding.totalPriceCardView.visibility = View.GONE
                binding.emptyBasketAnimation.playAnimation()
                adapter.updateBasketList(it)
            }
            else{
                binding.emptyBasketAnimation.visibility = View.GONE
                binding.emptyBasketTextView.visibility = View.GONE
                binding.totalPriceCardView.visibility = View.VISIBLE
                binding.emptyBasketAnimation.pauseAnimation()
            }
            adapter.updateBasketList(it)
        })

        basketViewModel.totalPriceLiveData.observe(viewLifecycleOwner, Observer {
            println("Total price oberver içinde")
            println("Total price: $it")
            binding.totalPriceTextView.text = it.toString()
        })

        adapter.onProductDeleteClick = {
            basketViewModel.removeFromBasket(it)
            mainActivityViewModel.getFirstBasketCount()
        }

        binding.buyNowButton.setOnClickListener {

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Do you want to make the purchase?")
            builder.setMessage("Total price: ${binding.totalPriceTextView.text}")

            builder.setPositiveButton("Yes"){dialog, which ->
                basketViewModel.buyNow()
                mainActivityViewModel.getFirstBasketCount()
            }
            builder.setNegativeButton("No"){dialog, which ->
                dialog.dismiss()
            }

            builder.show()


        }



    }


}











