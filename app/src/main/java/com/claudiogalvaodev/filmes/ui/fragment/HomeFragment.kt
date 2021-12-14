package com.claudiogalvaodev.filmes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.claudiogalvaodev.filmes.data.bd.entity.MovieEntity
import com.claudiogalvaodev.filmes.databinding.FragmentHomeBinding
import com.claudiogalvaodev.filmes.ui.adapter.PrincipalMoviesAdapter
import com.claudiogalvaodev.filmes.ui.viewmodel.HomeViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment: Fragment() {
    private val MAX_TRENDING_MOVIES = 10

    private val viewModel: HomeViewModel by viewModel()
    private val binding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = "Home"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTrendingMovies()
    }

    private fun getTrendingMovies() {
        viewModel.getTrendingMovies()
        viewModel.trendingMovies.observe(viewLifecycleOwner) { movies ->
            configTrendingMoviesList(movies.take(MAX_TRENDING_MOVIES))
        }
    }

    private fun configTrendingMoviesList(movies: List<MovieEntity>) {
        binding.fragmentHomeTrendingRecyclerview.apply {
            adapter = PrincipalMoviesAdapter(movies)
        }
    }
}