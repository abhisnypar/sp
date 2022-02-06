package com.spidugu.nycshools

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.spidugu.nycshools.databinding.ActivityMainBinding
import com.spidugu.nycshools.view.NYCSchoolDetailFragmentDirections

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHost) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}