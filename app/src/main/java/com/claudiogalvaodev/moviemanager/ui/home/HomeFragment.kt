package com.claudiogalvaodev.moviemanager.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.claudiogalvaodev.moviemanager.ui.model.EventModel
import com.claudiogalvaodev.moviemanager.ui.model.MovieModel
import com.claudiogalvaodev.moviemanager.databinding.FragmentHomeBinding
import com.claudiogalvaodev.moviemanager.ui.adapter.PrincipalMoviesAdapter
import com.claudiogalvaodev.moviemanager.ui.adapter.SimplePosterWithTitleAdapter
import com.claudiogalvaodev.moviemanager.ui.moviedetails.MovieDetailsActivity
import com.claudiogalvaodev.moviemanager.ui.speciallist.SpecialListActivity
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate

class HomeFragment: Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private val binding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    private val storage by lazy {
        FirebaseStorage.getInstance()
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

        setObservables()
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

        lifecycleScope.launchWhenStarted {
            viewModel.events.collectLatest { events ->
                configBannerSpecialList(events)
            }
        }
    }

    private fun configTrendingMoviesList(movieModels: List<MovieModel>) {
        binding.fragmentHomeTrendingRecyclerview.apply {
            val principalMoviesAdapter = PrincipalMoviesAdapter().apply {
                onItemClick = { movie ->
                    goToMovieDetails(movie.id, "")
                }
            }
            adapter = principalMoviesAdapter
            principalMoviesAdapter.submitList(movieModels)
        }
    }

    private fun configUpComingMoviesList(movieModels: List<MovieModel>) {
        binding.fragmentHomeComingUpRecyclerview.apply {
            val simplePosterAdapter = SimplePosterWithTitleAdapter().apply {
                onItemClick = { itemId, _, _, releaseDate ->
                    goToMovieDetails(itemId, releaseDate)
                }
            }
            adapter = simplePosterAdapter
            simplePosterAdapter.submitList(movieModels)
        }
    }

    private fun configLatestMoviesList(movieModels: List<MovieModel>) {
        binding.fragmentHomePlayingNowRecyclerview.apply {
            val simplePosterAdapter = SimplePosterWithTitleAdapter().apply {
                onItemClick = { itemId, _, _, releaseDate ->
                    goToMovieDetails(itemId, releaseDate)
                }
            }
            adapter = simplePosterAdapter
            simplePosterAdapter.submitList(movieModels)
        }
    }

    private fun configBannerSpecialList(events: List<EventModel>) {

        for (event in events) {
            val currentDate = LocalDate.now()
            val startAtToShowBanner = LocalDate.parse(event.startAt)
            val finishAtToShowBanner = LocalDate.parse(event.finishAt)
            if (currentDate.isAfter(startAtToShowBanner) &&
                currentDate.isBefore(finishAtToShowBanner)
            ) {
                val imageUrl = event.imageUrl
                try {
                    storage.getReferenceFromUrl(imageUrl).downloadUrl.addOnSuccessListener { uri ->
                        Picasso.with(context).load(uri).into(binding.bannerScpecialListImage)
                        showBanner(event)
                    }
                } catch (e: Exception) {
                    Log.e("firebase", "Something went wrong")
                }
            }
        }
    }

    private fun showBanner(event: EventModel) {
        binding.bannerSpecialListCardview.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                goToSpecialLists(event.id, event.title, event.description)
            }
        }
    }

    private fun goToSpecialLists(eventId: String, eventName: String, eventDescription: String) {
        context?.let {
            startActivity(SpecialListActivity.newInstance(it, eventId, eventName, eventDescription))
        }
    }

    private fun goToMovieDetails(movieId: Int, releaseDate: String) {
        context?.let {
            startActivity(MovieDetailsActivity.newInstance(it, movieId, releaseDate))
        }
    }
}