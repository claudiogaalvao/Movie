package com.claudiogalvaodev.moviemanager.ui.mylists

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.claudiogalvaodev.moviemanager.data.model.Movie
import com.claudiogalvaodev.moviemanager.databinding.FragmentMyListDetailsBinding
import com.claudiogalvaodev.moviemanager.ui.adapter.SimplePosterAdapter
import com.claudiogalvaodev.moviemanager.ui.moviedetails.MovieDetailsActivity

class MyListFragmentDetails: Fragment() {

    private val binding by lazy {
        FragmentMyListDetailsBinding.inflate(layoutInflater)
    }

    private lateinit var moviesAdapter: SimplePosterAdapter

    private val args: MyListFragmentDetailsArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()

    }

    private fun setupAdapter() {
        moviesAdapter = SimplePosterAdapter().apply {
            onItemClick = { movie ->
                goToMovieDetails(movie)
            }
        }
    }

    private fun goToMovieDetails(movie: Movie) {
        val intent = Intent(activity, MovieDetailsActivity::class.java)
        intent.putExtra("movieId", movie.id)
        startActivity(intent)
    }
}