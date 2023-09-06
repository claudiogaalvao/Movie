package com.claudiogalvaodev.moviemanager.ui.customLists.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.claudiogalvaodev.moviemanager.R
import com.claudiogalvaodev.moviemanager.databinding.ActivityCustomListsBinding

private const val ARG_CUSTOM_LIST_ID = "customListId"
private const val ARG_CUSTOM_LIST_NAME = "customListName"

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

        val customListId = intent.getIntExtra(ARG_CUSTOM_LIST_ID, 0)
        val customListName = intent.getStringExtra(ARG_CUSTOM_LIST_NAME) ?: ""

        initializeFragment(customListId, customListName)
        configToolbar()
    }

    private fun configToolbar() {
        setSupportActionBar(binding.activityMyListsToolbar)
    }

    private fun initializeFragment(customListId: Int, customListName: String) {
        val bundle = Bundle().apply {
            putInt("customListId", customListId)
            putString("customListName", customListName)
        }
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.activity_my_lists_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Config graph with different startDestination
        val graphInflater = navHostFragment.navController.navInflater
        val navGraph = graphInflater.inflate(R.navigation.main_without_bottom_nav_graph)
        navGraph.setStartDestination(R.id.myListFragmentDetails)
        navController.setGraph(navGraph, bundle)

        appBarConfiguration = AppBarConfiguration(navController.graph)
    }

    override fun onSupportNavigateUp(): Boolean {
        checkAndNavigateToPreviousActivity()
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        checkAndNavigateToPreviousActivity()
        navController.navigateUp(appBarConfiguration)
    }

    private fun checkAndNavigateToPreviousActivity() {
        if (navController.previousBackStackEntry?.id == null) {
            finish()
        }
    }

    fun setToolbarTitle(title: String) {
        binding.activityMyListsToolbarTitle.text = title
    }

    companion object {
        fun newInstance(context: Context, customListId: Int, customListName: String): Intent {
            val intent = Intent(context, CustomListsActivity::class.java)
            intent.putExtra(ARG_CUSTOM_LIST_ID, customListId)
            intent.putExtra(ARG_CUSTOM_LIST_NAME, customListName)
            return intent
        }
    }
}