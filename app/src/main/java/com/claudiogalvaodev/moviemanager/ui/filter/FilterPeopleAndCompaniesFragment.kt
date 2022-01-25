package com.claudiogalvaodev.moviemanager.ui.filter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isNotEmpty
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.claudiogalvaodev.moviemanager.R
import com.claudiogalvaodev.moviemanager.databinding.FragmentFilterPeopleAndCompaniesBinding
import com.claudiogalvaodev.moviemanager.model.Employe
import com.claudiogalvaodev.moviemanager.ui.adapter.CircleWithTitleAdapter
import com.claudiogalvaodev.moviemanager.ui.filter.FiltersActivity.Companion.KEY_BUNDLE_CURRENT_VALUE
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.viewmodel.ext.android.viewModel
import kotlin.math.roundToInt

class FilterPeopleAndCompaniesFragment: Fragment() {

    private val viewModel: FiltersViewModel by viewModel()
    private val binding by lazy {
        FragmentFilterPeopleAndCompaniesBinding.inflate(layoutInflater)
    }

    private lateinit var selectedPeopleAdapter: CircleWithTitleAdapter
    private lateinit var popularPeopleAdapter: CircleWithTitleAdapter
    private lateinit var currentValue: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentValue = arguments?.getString(KEY_BUNDLE_CURRENT_VALUE).orEmpty()

        setTitle()
        getPeople()
        setupRecyclerView()
        setObservables()
    }

    private fun setTitle() {
        (activity as FiltersActivity).setToolbarTitle(resources.getString(R.string.fragment_people_title))
    }

    private fun getPeople() {
        viewModel.getAllPeople()
    }

    private fun setupRecyclerView() {
        popularPeopleAdapter = CircleWithTitleAdapter()

        val layout = GridLayoutManager(context, calcNumberOfColumns())
        binding.fragmentPeopleAndCompaniesPopularActorsRecyclerview.apply {
            layoutManager = layout
            adapter = popularPeopleAdapter
        }
        setOnLoadMoreListener()
    }

    private fun setOnLoadMoreListener() {
        binding.fragmentFilterPeopleAndCompaniesNestedscroll.setOnScrollChangeListener { nestedView, scrollX, scrollY, oldScrollX, oldScrollY ->
            val child = nestedView.findViewById<RecyclerView>(R.id.fragment_people_and_companies_popular_actors_recyclerview)
            if(child.isNotEmpty()) {
                if ((scrollY >= (child.measuredHeight - nestedView.measuredHeight)) &&
                    scrollY > oldScrollY && !viewModel.isLoadingActors) {
                    getPeople()
                }
            }
        }
    }

    private fun setObservables() {
        lifecycleScope.launchWhenStarted {
            viewModel.people.collectLatest { people ->
                setPeople(people)
            }
        }
    }

    private fun setPeople(people: List<Employe>) {
        popularPeopleAdapter.submitList(people)
    }

    private fun calcNumberOfColumns(): Int {
        val displayMetrics = resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density

        val spaceBetween = 10
        val marginStart = 16
        val marginEnd = 16
        val widthEachImage = 100

        var countImages = dpWidth - marginStart - marginEnd
        countImages /= (widthEachImage+spaceBetween)
        return countImages.roundToInt()
    }

}