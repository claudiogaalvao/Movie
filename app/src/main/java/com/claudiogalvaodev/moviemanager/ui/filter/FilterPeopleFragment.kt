package com.claudiogalvaodev.moviemanager.ui.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import androidx.core.view.isNotEmpty
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.claudiogalvaodev.moviemanager.R
import com.claudiogalvaodev.moviemanager.databinding.FragmentFilterPeopleBinding
import com.claudiogalvaodev.moviemanager.data.model.Employe
import com.claudiogalvaodev.moviemanager.ui.adapter.CircleWithTitleAdapter
import com.claudiogalvaodev.moviemanager.ui.filter.FiltersActivity.Companion.KEY_BUNDLE_CURRENT_VALUE
import com.google.gson.Gson
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.viewmodel.ext.android.viewModel
import kotlin.math.roundToInt

class FilterPeopleFragment: Fragment() {

    private val viewModel: FiltersViewModel by viewModel()
    private val binding by lazy {
        FragmentFilterPeopleBinding.inflate(layoutInflater)
    }

    private lateinit var selectedPeopleAdapter: CircleWithTitleAdapter
    private lateinit var popularPeopleAdapter: CircleWithTitleAdapter
    private lateinit var currentValue: List<Employe>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCurrentValue()
        setTitle()
        getPeople(isInitialize = true)
        setupRecyclerView()
        setObservables()
        setListeners()
    }

    private fun setCurrentValue() {
        val jsonCurrentValue = arguments?.getString(KEY_BUNDLE_CURRENT_VALUE).orEmpty()
        currentValue = if (jsonCurrentValue.isNotBlank()) {
            Gson().fromJson(jsonCurrentValue, Array<Employe>::class.java).asList()
        } else {
            emptyList()
        }
        viewModel.initPeoplePreviousSelected(currentValue)
    }

    private fun setTitle() {
        (activity as FiltersActivity).setToolbarTitle(resources.getString(R.string.fragment_people_title))
    }

    private fun getPeople(isInitialize: Boolean = false) {
        viewModel.getAllPeople(isInitialize)
    }

    private fun setupRecyclerView() {
        selectedPeopleAdapter = CircleWithTitleAdapter()
        popularPeopleAdapter = CircleWithTitleAdapter()

        val selectedPeopleRecyclerViewLayout = GridLayoutManager(context, calcNumberOfColumns())
        val popularRecyclerViewLayout = GridLayoutManager(context, calcNumberOfColumns())

        binding.fragmentPeopleAndCompaniesActorsSelectedRecyclerview.apply {
            layoutManager = selectedPeopleRecyclerViewLayout
            adapter = selectedPeopleAdapter
        }

        binding.fragmentPeopleAndCompaniesPopularActorsRecyclerview.apply {
            layoutManager = popularRecyclerViewLayout
            adapter = popularPeopleAdapter
        }
        setOnLoadMoreListener()
    }

    private fun setOnLoadMoreListener() {
        binding.fragmentFilterPeopleAndCompaniesNestedscroll.setOnScrollChangeListener { nestedView, _, scrollY, _, oldScrollY ->
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
            viewModel.peopleSelected.collectLatest { people ->
                setSelectedPeople(people)
                var jsonString = Gson().toJson(people).orEmpty()
                if(jsonString == "[]") {
                    jsonString = ""
                    binding.directorChairIcon.visibility = View.VISIBLE
                    binding.filterSelectDescription.visibility = View.VISIBLE
                } else if(binding.directorChairIcon.isVisible) {
                    binding.directorChairIcon.visibility = View.GONE
                    binding.filterSelectDescription.visibility = View.GONE
                }
                (activity as FiltersActivity).changeCurrentValue(jsonString)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.people.collectLatest { people ->
                 setPeople(people)
            }
        }
    }

    private fun setListeners() {
        selectedPeopleAdapter.onItemClick = {
                obj ->
            when(obj) {
                is Employe -> {
                     viewModel.unselectPerson(obj)
                }
            }
        }

        popularPeopleAdapter.onItemClick = { obj ->
            when(obj) {
                is Employe -> {
                     viewModel.selectPerson(obj)
                }
            }
        }

        binding.filterButtonApply.setOnClickListener {
            (activity as FiltersActivity).saveChangesAndNavigateToPreviousActivity()
        }
    }

    private fun setSelectedPeople(people: List<Employe>) {
        selectedPeopleAdapter.submitList(people)
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