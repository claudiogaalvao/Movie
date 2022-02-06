package com.claudiogalvaodev.moviemanager.ui.mylists

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.claudiogalvaodev.moviemanager.R
import com.claudiogalvaodev.moviemanager.databinding.ActivityMyListsBinding

class MyListsActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMyListsBinding.inflate(layoutInflater)
    }

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        configToolbar()
    }

    private fun configToolbar() {
        setSupportActionBar(binding.activityMyListsToolbar)
    }

    fun setToolbarTitle(title: String) {
        binding.activityMyListsToolbarTitle.text = title
    }
}