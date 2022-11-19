package com.claudiogalvaodev.moviemanager.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.claudiogalvaodev.moviemanager.R
import com.claudiogalvaodev.moviemanager.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(2000)
        setTheme(R.style.Theme_Filmes)

        setContentView(binding.root)

        setNavigationController()
    }

    private fun setNavigationController() {
        setSupportActionBar(binding.activityMainToolbar)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.activity_main_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.activityMainBottomNavigation.setupWithNavController(navController)
    }

    fun showBottomNavigation() {
        binding.activityMainBottomNavigation.visibility = View.VISIBLE
    }

    fun hideBottomNavigation() {
        binding.activityMainBottomNavigation.visibility = View.GONE
    }
}