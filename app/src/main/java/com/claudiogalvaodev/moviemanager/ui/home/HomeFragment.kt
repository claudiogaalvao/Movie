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
import com.claudiogalvaodev.moviemanager.ui.speciallist.SpecialListActivity
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.viewmodel.ext.android.viewModel
import java.time.LocalDate

class HomeFragment: Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private val binding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    private val limitDateToShowOscarBanner = LocalDate.parse("2022-04-10")

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
        configBannerSpecialList()
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
                    goToMovieDetails(movie.id)
                }
            }
            adapter = principalMoviesAdapter
            principalMoviesAdapter.submitList(movies)
        }
    }

    private fun configUpComingMoviesList(movies: List<Movie>) {
        binding.fragmentHomeComingUpRecyclerview.apply {
            val simplePosterAdapter = SimplePosterWithTitleAdapter().apply {
                onItemClick = { itemId, _, _ ->
                    goToMovieDetails(itemId)
                }
            }
            adapter = simplePosterAdapter
            simplePosterAdapter.submitList(movies)
        }
    }

    private fun configLatestMoviesList(movies: List<Movie>) {
        binding.fragmentHomePlayingNowRecyclerview.apply {
            val simplePosterAdapter = SimplePosterWithTitleAdapter().apply {
                onItemClick = { itemId, _, _ ->
                    goToMovieDetails(itemId)
                }
            }
            adapter = simplePosterAdapter
            simplePosterAdapter.submitList(movies)
        }
    }

    private fun configBannerSpecialList() {
        if(LocalDate.now().isAfter(limitDateToShowOscarBanner)) binding.bannerSpecialListCardview.visibility = View.GONE
        binding.bannerSpecialListCardview.setOnClickListener {
            goToSpecialLists()
        }
    }

    private fun goToSpecialLists() {
        val intent = Intent(context, SpecialListActivity::class.java)
        startActivity(intent)
    }

    private fun goToMovieDetails(movieId: Int) {
        context?.let {
            startActivity(MovieDetailsActivity.newInstance(it, movieId))
        }
    }
}