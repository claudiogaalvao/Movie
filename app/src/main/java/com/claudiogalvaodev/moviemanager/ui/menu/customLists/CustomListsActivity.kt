package com.claudiogalvaodev.moviemanager.ui.menu.customLists

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.claudiogalvaodev.moviemanager.R
import com.claudiogalvaodev.moviemanager.databinding.ActivityCustomListsBinding

class CustomListsActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCustomListsBinding.inflate(layoutInflater)
    }

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Filmes)
        setContentView(binding.root)

        configToolbar()
    }

    private fun configToolbar() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.activity_my_lists_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)

        setSupportActionBar(binding.activityMyListsToolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navController.addOnDestinationChangedListener { _, _, _ ->
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        checkAndNavigateToPreviousActivity()
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        checkAndNavigateToPreviousActivity()
    }

    private fun checkAndNavigateToPreviousActivity() {
        if (navController.previousBackStackEntry?.id == null) {
            finish()
        } else {
            navController.popBackStack()
        }
    }

    fun setToolbarTitle(title: String) {
        binding.activityMyListsToolbarTitle.text = title
    }
}