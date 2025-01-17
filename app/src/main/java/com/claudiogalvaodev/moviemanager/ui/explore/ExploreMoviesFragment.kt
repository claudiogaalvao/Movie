package com.claudiogalvaodev.moviemanager.ui.explore

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.claudiogalvaodev.moviemanager.databinding.FragmentExploreMoviesBinding
import com.claudiogalvaodev.moviemanager.ui.model.MovieModel
import com.claudiogalvaodev.moviemanager.ui.adapter.FilterAdapter
import com.claudiogalvaodev.moviemanager.ui.adapter.SimplePosterAdapter
import com.claudiogalvaodev.moviemanager.ui.filter.FiltersActivity
import com.claudiogalvaodev.moviemanager.ui.moviedetails.MovieDetailsActivity
import com.claudiogalvaodev.moviemanager.utils.extensions.launchWhenResumed
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExploreMoviesFragment: Fragment() {

    private val viewModel: ExploreMoviesViewModel by viewModel()
    private val binding by lazy {
        FragmentExploreMoviesBinding.inflate(layoutInflater)
    }

    private val filterContract = registerForActivityResult(FiltersActivity.Contract()) { result ->
        result?.let {
            viewModel.updateFilter(result)
        }
    }

    private lateinit var filtersAdapter: FilterAdapter
    private lateinit var moviesAdapter: SimplePosterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
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
        setupAdapter()
        setupRecyclerView()
        setObservables()
    }

    private fun getMovies() {
        viewModel.getMoviesByCriterious()
    }

    private fun setupAdapter() {
        filtersAdapter = FilterAdapter().apply {
            onItemClick = { filter ->
                filterContract.launch(filter)
            }
        }

        moviesAdapter = SimplePosterAdapter().apply {
            onItemClick = { movieId ->
                goToMovieDetails(movieId, "")
            }
        }
    }

    private fun setupRecyclerView() {
        binding.fragmentExploreFiltersRecyclerview.adapter = filtersAdapter

        val numberOfColumns = calcNumberOfColumns()
        val layout = GridLayoutManager(context, numberOfColumns)
        binding.fragmentExploreMoviesRecyclerview.apply {
            layoutManager = layout
            adapter = moviesAdapter
        }
        setOnLoadMoreListener()
    }

    private fun setOnLoadMoreListener() {
        binding.fragmentExploreMoviesRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(dy <= 0) return

                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount

                val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
                val isNotAtBeginning = firstVisibleItemPosition >= 0
                val shouldPaginate = isNotAtBeginning && isAtLastItem && isNotAtBeginning
                if(shouldPaginate && !viewModel.isLoading) {
                    getMovies()
                }
            }
        })
    }

    private fun setObservables() {
        launchWhenResumed {
            viewModel.movies.collectLatest { movies ->
                submitMoviesList(movies)
                if(viewModel.getSecondPage) getMovies()
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.filters.collectLatest { filters ->
                    filtersAdapter.submitList(null)
                    filtersAdapter.submitList(filters)
                }
            }
        }
    }

    private fun submitMoviesList(movieModels: List<MovieModel>) {
        if(movieModels.isEmpty() && !viewModel.isLoading) {
            binding.wathingIcon.visibility = View.VISIBLE
            binding.exploreMoviesDidntFindDescription.visibility = View.VISIBLE
        } else if(binding.wathingIcon.isVisible) {
            binding.wathingIcon.visibility = View.GONE
            binding.exploreMoviesDidntFindDescription.visibility = View.GONE
        }
        moviesAdapter.submitList(movieModels)
    }

    private fun goToMovieDetails(movieId: Int, releaseDate: String) {
        context?.let {
            startActivity(MovieDetailsActivity.newInstance(it, movieId, releaseDate))
        }
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
        val numberOfColumns = countImages.roundToInt()
        if(numberOfColumns > 4) {
            viewModel.getSecondPage = true
        }
        return numberOfColumns
    }

}