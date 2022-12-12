package com.example.e_commerceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavHost
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.e_commerceapp.Views.HomePageFragmentDirections
import com.example.e_commerceapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.BottomNavView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.favouritePageFragment) {
                val aciton =
                    HomePageFragmentDirections.actionHomePageFragmentToFavouritePageFragment()
                navController.navigate(aciton)
            }

            if (destination.id == R.id.profilePageFragment){
                val action = HomePageFragmentDirections.actionHomePageFragmentToProfilePageFragment()
                navController.navigate(action)

            }


        }

        setContentView(view)


    }
}