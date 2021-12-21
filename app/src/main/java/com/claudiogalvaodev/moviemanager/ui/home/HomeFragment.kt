package com.claudiogalvaodev.moviemanager.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.claudiogalvaodev.moviemanager.data.bd.entity.MovieEntity
import com.claudiogalvaodev.moviemanager.databinding.FragmentHomeBinding
import com.claudiogalvaodev.moviemanager.ui.adapter.PrincipalMoviesAdapter
import com.claudiogalvaodev.moviemanager.ui.adapter.SimplePosterWithTitleAdapter
import com.claudiogalvaodev.moviemanager.utils.constants.MaxMoviesToShow.MAX_LATEST_MOVIES
import com.claudiogalvaodev.moviemanager.utils.constants.MaxMoviesToShow.MAX_TRENDING_MOVIES
import com.claudiogalvaodev.moviemanager.utils.constants.MaxMoviesToShow.MAX_UPCOMING_MOVIES
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
        viewModel.getUpComingMovies()
        viewModel.getPlayingNowMovies()
    }

    private fun setObservables() {
        lifecycleScope.launchWhenStarted {
            viewModel.trendingMovies.collectLatest { movies ->
                configTrendingMoviesList(movies.take(MAX_TRENDING_MOVIES))
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.upComingMovies.collectLatest { movies ->
                configUpComingMoviesList(movies.take(MAX_UPCOMING_MOVIES))
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.playingNowMovies.collectLatest { movies ->
                configLatestMoviesList(movies.take(MAX_LATEST_MOVIES))
            }
        }
    }

    private fun configTrendingMoviesList(movies: List<MovieEntity>) {
        binding.fragmentHomeTrendingRecyclerview.apply {
            adapter = PrincipalMoviesAdapter(movies).apply {
                onItemClick = { movie ->
                    goToMovieDetails(movie)
                }
            }
        }
    }

    private fun configUpComingMoviesList(movies: List<MovieEntity>) {
        binding.fragmentHomeComingUpRecyclerview.apply {
            adapter = SimplePosterWithTitleAdapter(movies).apply {
                onItemClick = { movie ->
                    goToMovieDetails(movie)
                }
            }
        }
    }

    private fun configLatestMoviesList(movies: List<MovieEntity>) {
        binding.fragmentHomePlayingNowRecyclerview.apply {
            adapter = SimplePosterWithTitleAdapter(movies).apply {
                onItemClick = { movie ->
                    goToMovieDetails(movie)
                }
            }
        }
    }

    private fun goToMovieDetails(movie: MovieEntity) {
        val directions =
            HomeFragmentDirections.actionHomeFragmentToMovieDetailsActivity(movie)
        findNavController().navigate(directions)
    }
}