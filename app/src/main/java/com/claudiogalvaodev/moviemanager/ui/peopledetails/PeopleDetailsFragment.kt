package com.claudiogalvaodev.moviemanager.ui.peopledetails

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.claudiogalvaodev.moviemanager.R
import com.claudiogalvaodev.moviemanager.databinding.FragmentPeopleDetailsBinding
import com.claudiogalvaodev.moviemanager.model.Employe
import com.claudiogalvaodev.moviemanager.model.Movie
import com.claudiogalvaodev.moviemanager.ui.adapter.SimplePosterAdapter
import com.claudiogalvaodev.moviemanager.ui.moviedetails.MovieDetailsActivity
import com.claudiogalvaodev.moviemanager.utils.format.formatUtils
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.viewmodel.ext.android.viewModel
import kotlin.math.roundToInt

class PeopleDetailsFragment : Fragment() {
    private var employe: Employe? = null

    private val viewModel: PeopleDetailsViewModel by viewModel()
    private val binding by lazy {
        FragmentPeopleDetailsBinding.inflate(layoutInflater)
    }
    private lateinit var moviesAdapter: SimplePosterAdapter

    private val args: PeopleDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        employe = args.employeDetails
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isUpdate = true

        getPeopleDetails()
        getMovies()
        bindHeaderInfo()
        setupAdapter()
        setupRecyclerView()
        setupObservers()
    }

    private fun getPeopleDetails() {
        viewModel.getPersonDetails(employe?.id.toString())
    }

    private fun getMovies() {
        viewModel.getMovies(employe?.id.toString())
    }

    private fun bindHeaderInfo() {
        employe?.let { empl ->
            Picasso.with(binding.root.context).load(empl.getProfileImageUrl())
                .into(binding.fragmentPeopleDetailsHeader.fragmentPeopleDetailsProfilePhoto)
            binding.fragmentPeopleDetailsHeader.fragmentPeopleDetailsName.text = empl.name
            binding.fragmentPeopleDetailsHeader.fragmentPeopleDetailsDepartment.text = empl.known_for_department
        }
    }

    private fun setupAdapter() {
        moviesAdapter = SimplePosterAdapter().apply {
            onItemClick = { movie ->
                goToMovieDetails(movie)
            }
        }
    }

    private fun setupRecyclerView() {
        val layout = GridLayoutManager(context, calcNumberOfColumns())
        binding.fragmentPeopleDetailsMoviesRecyclerview.apply {
            layoutManager = layout
            adapter = moviesAdapter
        }
        setOnLoadMoreListener()
    }

    private fun setOnLoadMoreListener() {
        binding.fragmentPeopleDetailsMoviesRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
                if(shouldPaginate && !viewModel.isMoviesLoading) {
                    getMovies()
                }
            }
        })
    }

    private fun setupObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.movies.collectLatest { movies ->
                setMoviesList(movies)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.personDetails.collectLatest { person ->
                bindPersonDetailsInfo(person)
            }
        }
    }

    private fun bindPersonDetailsInfo(person: Employe?) {
        person?.let {
            binding.fragmentPeopleDetailsBiography.text = it.biography
            binding.fragmentPeopleDetailsHeader.fragmentPeopleDetailsBirthdate.text =
                formatUtils.dateFromAmericanFormatToDateWithMonthName(person.birthday)

            binding.fragmentPeopleDetailsHeader.fragmentPeopleDetailsAge.text = if(!person.deathday.isNullOrEmpty()) {
                formatUtils.dateFromAmericanFormatToDateWithMonthName(person.deathday)
            } else {
                "${formatUtils.dateFromAmericanFormatToAge(person.birthday)} ${context?.resources?.getString(R.string.age_label)}"
            }

            binding.fragmentPeopleDetailsHeader.fragmentPeopleDetailsBirthplace.text = person.place_of_birth
        }
    }

    private fun setMoviesList(movies: List<Movie>) {
        moviesAdapter.submitList(movies)
    }

    private fun goToMovieDetails(movie: Movie) {
        val intent = Intent(activity, MovieDetailsActivity::class.java)
        intent.putExtra("movieId", movie.id)
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