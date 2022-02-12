package com.claudiogalvaodev.moviemanager.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.claudiogalvaodev.moviemanager.R
import com.claudiogalvaodev.moviemanager.databinding.ActivityMainBinding
import com.claudiogalvaodev.moviemanager.ui.behavior.BottomNavigationBehavior

class MainActivity: AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(binding.root)

        setNavigationController()
        setupBottomNavigationBehavior()
    }

    private fun setNavigationController() {
        setSupportActionBar(binding.activityMainToolbar)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.activity_main_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.activityMainBottomNavigation.setupWithNavController(navController)
    }

    private fun setupBottomNavigationBehavior() {
        val navigationLayoutParams = binding.activityMainBottomNavigation.layoutParams as CoordinatorLayout.LayoutParams
        navigationLayoutParams.behavior = BottomNavigationBehavior()
    }
}