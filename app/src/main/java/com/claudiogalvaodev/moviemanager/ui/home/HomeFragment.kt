package com.claudiogalvaodev.moviemanager.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.claudiogalvaodev.moviemanager.data.model.Movie
import com.claudiogalvaodev.moviemanager.databinding.FragmentHomeBinding
import com.claudiogalvaodev.moviemanager.ui.adapter.PrincipalMoviesAdapter
import com.claudiogalvaodev.moviemanager.ui.adapter.SimplePosterWithTitleAdapter
import com.claudiogalvaodev.moviemanager.ui.moviedetails.MovieDetailsActivity
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment: Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private val binding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMovies()
        setObservables()
    }

    private fun getMovies() {
        viewModel.getTrendingMovies()
        viewModel.getUpComingAndPlayingNow()
    }

    private fun setObservables() {
        lifecycleScope.launchWhenStarted {
            viewModel.trendingMovies.collectLatest { movies ->
                configTrendingMoviesList(movies)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.upComingMovies.collectLatest { movies ->
                configUpComingMoviesList(movies)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.playingNowMovies.collectLatest { movies ->
                configLatestMoviesList(movies)
            }
        }
    }

    private fun configTrendingMoviesList(movies: List<Movie>) {
        binding.fragmentHomeTrendingRecyclerview.apply {
            val principalMoviesAdapter = PrincipalMoviesAdapter().apply {
                onItemClick = { movie ->
                    goToMovieDetails(movie)
                }
            }
            adapter = principalMoviesAdapter
            principalMoviesAdapter.submitList(movies)
        }
    }

    private fun configUpComingMoviesList(movies: List<Movie>) {
        binding.fragmentHomeComingUpRecyclerview.apply {
            val simplePosterAdapter = SimplePosterWithTitleAdapter().apply {
                onItemClick = { movie ->
                    goToMovieDetails(movie)
                }
            }
            adapter = simplePosterAdapter
            simplePosterAdapter.submitList(movies)
        }
    }

    private fun configLatestMoviesList(movies: List<Movie>) {
        binding.fragmentHomePlayingNowRecyclerview.apply {
            val simplePosterAdapter = SimplePosterWithTitleAdapter().apply {
                onItemClick = { movie ->
                    goToMovieDetails(movie)
                }
            }
            adapter = simplePosterAdapter
            simplePosterAdapter.submitList(movies)
        }
    }

    private fun goToMovieDetails(movie: Movie) {
        val intent = Intent(activity, MovieDetailsActivity::class.java)
        intent.putExtra("movieId", movie.id)
        startActivity(intent)
    }
}