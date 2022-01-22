package com.claudiogalvaodev.moviemanager.ui.peopleandcompanies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.claudiogalvaodev.moviemanager.databinding.FragmentPeopleAndCompaniesBinding
import com.claudiogalvaodev.moviemanager.model.Employe
import com.claudiogalvaodev.moviemanager.ui.adapter.CircleWithTitleAdapter
import com.claudiogalvaodev.moviemanager.ui.moviedetails.MovieDetailsActivity
import androidx.recyclerview.widget.GridLayoutManager

import android.util.DisplayMetrics
import android.view.Display
import com.claudiogalvaodev.moviemanager.R
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

        circleWithTitleAdapter = CircleWithTitleAdapter()

        val employeList = args.employeList
        if(employeList != null) {
            (activity as MovieDetailsActivity).setToolbarTitle(resources.getString(R.string.fragment_actors_title))
            configActorsList(employeList)
            setupRecyclerViewLayoutManager()
        }
    }

    private fun configActorsList(actors: Array<Employe>) {
        binding.fragmentPeopleAndCompaniesActorsSelectedTitle.visibility = View.GONE
        binding.fragmentPeopleAndCompaniesPopularActorsTitle.visibility = View.GONE
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



}