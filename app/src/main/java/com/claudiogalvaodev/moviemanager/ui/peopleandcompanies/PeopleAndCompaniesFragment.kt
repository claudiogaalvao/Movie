package com.claudiogalvaodev.moviemanager.ui.peopleandcompanies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.claudiogalvaodev.moviemanager.R
import com.claudiogalvaodev.moviemanager.databinding.FragmentPeopleAndCompaniesBinding
import com.claudiogalvaodev.moviemanager.ui.adapter.CircleWithTitleAdapter
import com.claudiogalvaodev.moviemanager.ui.model.PersonModel
import com.claudiogalvaodev.moviemanager.ui.moviedetails.MovieDetailsActivity
import com.claudiogalvaodev.moviemanager.utils.extensions.launchWhenResumed
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf
import kotlin.math.roundToInt

class PeopleAndCompaniesFragment: Fragment() {

    private lateinit var viewModel: PeopleAndCompaniesViewModel
    private val binding by lazy {
        FragmentPeopleAndCompaniesBinding.inflate(layoutInflater)
    }
    private lateinit var circleWithTitleAdapter: CircleWithTitleAdapter

    private val args: PeopleAndCompaniesFragmentArgs by navArgs()

    private val movieId by lazy {
        args.movieId
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

        viewModel = getViewModel { parametersOf(movieId) }
        (activity as MovieDetailsActivity).setToolbarTitle(resources.getString(R.string.fragment_actors_title))

        getCredits()
        setupAdapter()
        setupObservers()
    }

    private fun setupAdapter() {
        circleWithTitleAdapter = CircleWithTitleAdapter().apply {
            onItemClick = { obj ->
                if (obj is PersonModel) {
                    goToPeopleDetails(obj)
                }
            }
        }
    }

    private fun getCredits() {
        viewModel.getMovieCredits()
    }

    private fun setupObservers() {
        launchWhenResumed {
            viewModel.actors.collectLatest { people ->
                people?.let {
                    configPeopleList(it)
                    setupRecyclerViewLayoutManager()
                }
            }
        }
    }

    private fun configPeopleList(people: List<PersonModel>) {
        binding.fragmentPeopleAndCompaniesPopularActorsRecyclerview.adapter = circleWithTitleAdapter
        circleWithTitleAdapter.submitList(people)
    }

    private fun setupRecyclerViewLayoutManager() {
        val itemSize = 120
        val displayMetrics = resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        val columns = (dpWidth / itemSize).roundToInt()
        val layoutManager = GridLayoutManager(activity, columns)
        binding.fragmentPeopleAndCompaniesPopularActorsRecyclerview.layoutManager = layoutManager
    }

    private fun goToPeopleDetails(person: PersonModel) {
        val directions = PeopleAndCompaniesFragmentDirections
            .actionPeopleAndCompaniesFragmentToPeopleDetailsFragment2(person.id.toLong(), movieId)
        findNavController().navigate(directions)
    }

}