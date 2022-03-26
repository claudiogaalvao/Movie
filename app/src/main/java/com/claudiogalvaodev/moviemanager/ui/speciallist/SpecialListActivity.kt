package com.claudiogalvaodev.moviemanager.ui.speciallist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.claudiogalvaodev.moviemanager.R
import com.claudiogalvaodev.moviemanager.databinding.ActivitySpecialListBinding

private const val ARG_EVENT_ID = "eventId"
private const val ARG_EVENT_NAME = "eventName"

class SpecialListActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivitySpecialListBinding.inflate(layoutInflater)
    }

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Filmes)
        setContentView(binding.root)

        val eventId = intent.getStringExtra(ARG_EVENT_ID) ?: ""
        val eventName = intent.getStringExtra(ARG_EVENT_NAME) ?: ""

        initializeFragment(eventId, eventName)
        configToolbar()
    }

    private fun configToolbar() {
        setSupportActionBar(binding.activitySpecialListsToolbar)
    }

    private fun initializeFragment(eventId: String, eventName: String) {
        val bundle = Bundle().apply {
            putString("eventId", eventId)
        }

        binding.activitySpecialListsToolbarTitle.text = eventName
        val navHostFragment = supportFragmentManager
            .findFragmentById(binding.activitySpecialListsNavHost.id) as NavHostFragment
        navController = navHostFragment.navController

        // Config graph with different startDestination
        val graphInflater = navHostFragment.navController.navInflater
        val navGraph = graphInflater.inflate(R.navigation.main_without_bottom_nav_graph)
        navGraph.setStartDestination(R.id.oscarListFragment)
        navController.setGraph(navGraph, bundle)

        appBarConfiguration = AppBarConfiguration(navController.graph)
    }

    override fun onSupportNavigateUp(): Boolean {
        checkAndNavigateToPreviousActivity()
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        checkAndNavigateToPreviousActivity()
        navController.navigateUp(appBarConfiguration)
    }

    private fun checkAndNavigateToPreviousActivity() {
        if (navController.previousBackStackEntry?.id == null) {
            finish()
        }
    }

    companion object {
        fun newInstance(context: Context, eventId: String, eventName: String): Intent {
            val intent = Intent(context, SpecialListActivity::class.java)
            intent.putExtra(ARG_EVENT_ID, eventId)
            intent.putExtra(ARG_EVENT_NAME, eventName)
            return intent
        }
    }
}