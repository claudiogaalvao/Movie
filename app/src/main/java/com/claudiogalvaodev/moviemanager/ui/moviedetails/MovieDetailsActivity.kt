package com.claudiogalvaodev.moviemanager.ui.moviedetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.claudiogalvaodev.moviemanager.R
import com.claudiogalvaodev.moviemanager.databinding.ActivityMovieDetailsBinding

private const val ARG_MOVIE_ID = "movieId"
private const val ARG_RELEASE_DATE = "releaseDate"

class MovieDetailsActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMovieDetailsBinding.inflate(layoutInflater)
    }

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Filmes)
        setContentView(binding.root)

        val movieId = intent.getIntExtra(ARG_MOVIE_ID, 0)
        val releaseDate = intent.getStringExtra(ARG_RELEASE_DATE) ?: ""

        initializeFragment(movieId, releaseDate)
        configToolbar()
    }

    private fun configToolbar() {
        setSupportActionBar(binding.activityMovieDetailsToolbar)
    }

    private fun initializeFragment(movieId: Int, releaseDate: String) {
        val bundle = Bundle().apply {
            putLong("movieId", movieId.toLong())
            putString("releaseDate", releaseDate)
        }
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.activity_movie_details_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Config graph with different startDestination
        val graphInflater = navHostFragment.navController.navInflater
        val navGraph = graphInflater.inflate(R.navigation.main_without_bottom_nav_graph)
        navGraph.setStartDestination(R.id.movieDetailsFragment)
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

    fun setToolbarTitle(title: String) {
        binding.activityMovieDetailsToolbarTitle.text = title
    }

    companion object {
        fun newInstance(context: Context, movieId: Int, releaseDate: String): Intent {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra(ARG_MOVIE_ID, movieId)
            intent.putExtra(ARG_RELEASE_DATE, releaseDate)
            return intent
        }
    }

}