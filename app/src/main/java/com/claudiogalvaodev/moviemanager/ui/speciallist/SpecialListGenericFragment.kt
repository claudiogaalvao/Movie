package com.claudiogalvaodev.moviemanager.ui.speciallist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.claudiogalvaodev.moviemanager.databinding.FragmentSpecialListGenericBinding
import com.claudiogalvaodev.moviemanager.ui.adapter.SecondaryPosterWithTitleAdapter
import com.claudiogalvaodev.moviemanager.ui.moviedetails.MovieDetailsActivity
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class SpecialListGenericFragment: Fragment() {

    private lateinit var viewModel: SpecialListViewModel
    private val binding by lazy {
        FragmentSpecialListGenericBinding.inflate(layoutInflater)
    }

    private val args: SpecialListGenericFragmentArgs by navArgs()
    private val eventId by lazy {
        args.eventId
    }
    private val eventDescription by lazy {
        args.eventDescription
    }

    private lateinit var moviesAdapter: SecondaryPosterWithTitleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = getViewModel { parametersOf(eventId) }

        binding.description.text = eventDescription

        setupAdapter()
        setObservers()
    }

    private fun setupAdapter() {
        moviesAdapter = SecondaryPosterWithTitleAdapter().apply {
            onItemClick = { movieId, releaseDate ->
                goToMovieDetails(movieId, releaseDate)
            }
        }
        binding.fragmentExploreMoviesRecyclerview.adapter = moviesAdapter
    }

    private fun setObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.specialItem.collectLatest { specialItem ->
                moviesAdapter.submitList(specialItem)
            }
        }
    }

    private fun goToMovieDetails(movieId: Int, releaseDate: String) {
        context?.let {
            startActivity(MovieDetailsActivity.newInstance(it, movieId, releaseDate))
        }
    }

}