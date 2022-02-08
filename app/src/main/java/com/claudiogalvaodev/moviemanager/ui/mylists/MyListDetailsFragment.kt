package com.claudiogalvaodev.moviemanager.ui.mylists

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.claudiogalvaodev.moviemanager.R
import com.claudiogalvaodev.moviemanager.data.model.Movie
import com.claudiogalvaodev.moviemanager.databinding.FragmentMyListDetailsBinding
import com.claudiogalvaodev.moviemanager.ui.adapter.SimplePosterAdapter
import com.claudiogalvaodev.moviemanager.ui.moviedetails.MovieDetailsActivity
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.viewmodel.ext.android.viewModel
import kotlin.math.roundToInt

class MyListDetailsFragment: Fragment() {

    private val viewModel: MyListsViewModel by viewModel()
    private val binding by lazy {
        FragmentMyListDetailsBinding.inflate(layoutInflater)
    }

    private lateinit var moviesAdapter: SimplePosterAdapter

    private val args: MyListDetailsFragmentArgs by navArgs()
    private val listId by lazy {
        args.myListId
    }
    private val listName by lazy {
        args.myListName
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

        (activity as MyListsActivity).setToolbarTitle(listName)

        getData()
        setupAdapter()
        setupRecyclerView()
        setupObservers()
    }

    private fun getData() {
        viewModel.getMoviesByMyListId(listId)
    }

    private fun setupAdapter() {
        moviesAdapter = SimplePosterAdapter().apply {
            onItemClick = { movieId ->
                goToMovieDetails(movieId)
            }
        }
    }

    private fun setupRecyclerView() {
        val layout = GridLayoutManager(context, calcNumberOfColumns())
        binding.myListDetailsRecyclerview.apply {
            layoutManager = layout
            adapter = moviesAdapter
        }
    }

    private fun setupObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.movies.collectLatest { movies ->
                moviesAdapter.submitList(movies)
            }
        }
    }

    private fun goToMovieDetails(movieId: Int) {
        val intent = Intent(activity, MovieDetailsActivity::class.java)
        intent.putExtra("movieId", movieId)
        startActivity(intent)
    }

    private fun calcNumberOfColumns(): Int {
        val displayMetrics = resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density

        val spaceBetween = 12
        val marginStart = 16
        val marginEnd = 16
        val widthEachImage = 120

        var countImages = dpWidth - marginStart - marginEnd
        countImages /= (widthEachImage+spaceBetween)
        return countImages.roundToInt()
    }
}