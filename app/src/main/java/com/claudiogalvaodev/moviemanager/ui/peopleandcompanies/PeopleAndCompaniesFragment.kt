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
import com.claudiogalvaodev.moviemanager.model.Employe
import com.claudiogalvaodev.moviemanager.ui.adapter.CircleWithTitleAdapter
import com.claudiogalvaodev.moviemanager.ui.moviedetails.MovieDetailsActivity
import com.claudiogalvaodev.moviemanager.ui.moviedetails.MovieDetailsFragmentDirections
import kotlin.math.roundToInt


class PeopleAndCompaniesFragment: Fragment() {

    private val binding by lazy {
        FragmentPeopleAndCompaniesBinding.inflate(layoutInflater)
    }
    private lateinit var circleWithTitleAdapter: CircleWithTitleAdapter

    private val args: PeopleAndCompaniesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        circleWithTitleAdapter = CircleWithTitleAdapter().apply {
            onItemClick = { obj ->
                if(obj is Employe) {
                    goToPeopleDetails(obj)
                }
            }
        }

        val employeList = args.employeList
        if(employeList != null) {
            (activity as MovieDetailsActivity).setToolbarTitle(resources.getString(R.string.fragment_actors_title))
            configActorsList(employeList)
            setupRecyclerViewLayoutManager()
        }
    }

    private fun configActorsList(actors: Array<Employe>) {
        binding.fragmentPeopleAndCompaniesPopularActorsRecyclerview.adapter = circleWithTitleAdapter
        circleWithTitleAdapter.submitList(actors.asList())
    }

    private fun setupRecyclerViewLayoutManager() {
        val itemSize = 120
        val displayMetrics = resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        val columns = (dpWidth / itemSize).roundToInt()
        val layoutManager = GridLayoutManager(activity, columns)
        binding.fragmentPeopleAndCompaniesPopularActorsRecyclerview.layoutManager = layoutManager
    }

    private fun goToPeopleDetails(employe: Employe) {
        val directions = PeopleAndCompaniesFragmentDirections
            .actionPeopleAndCompaniesFragmentToPeopleDetailsFragment2(employe)
        findNavController().navigate(directions)
    }

}