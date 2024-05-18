package com.claudiogalvaodev.moviemanager.ui.filter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isNotEmpty
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.claudiogalvaodev.moviemanager.R
import com.claudiogalvaodev.moviemanager.databinding.FragmentFilterPeopleBinding
import com.claudiogalvaodev.moviemanager.ui.adapter.CircleWithTitleAdapter
import com.claudiogalvaodev.moviemanager.ui.filter.FiltersActivity.Companion.KEY_BUNDLE_CURRENT_VALUE
import com.claudiogalvaodev.moviemanager.ui.model.PersonModel
import com.claudiogalvaodev.moviemanager.utils.extensions.launchWhenResumed
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.roundToInt

class FilterPeopleFragment: Fragment() {

    private val viewModel: FiltersViewModel by viewModel()
    private val binding by lazy {
        FragmentFilterPeopleBinding.inflate(layoutInflater)
    }

    private lateinit var searchResultAdapter: CircleWithTitleAdapter
    private lateinit var selectedPeopleAdapter: CircleWithTitleAdapter
    private lateinit var popularPeopleAdapter: CircleWithTitleAdapter
    private lateinit var currentValue: List<PersonModel>

    private var isSearchInitialized = false

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
        getPeople(isInitialize = true)
        setupRecyclerView()
        setObservables()
        setListeners()
    }

    private fun searchPeople(query: String) {
        isSearchInitialized = true
        viewModel.searchPeople(query)
    }

    private fun setCurrentValue() {
        val jsonCurrentValue = arguments?.getString(KEY_BUNDLE_CURRENT_VALUE).orEmpty()
        currentValue = if (jsonCurrentValue.isNotBlank()) {
            Gson().fromJson(jsonCurrentValue, Array<PersonModel>::class.java).asList()
        } else {
            emptyList()
        }
        viewModel.initPeoplePreviousSelected(currentValue)
    }

    private fun getPeople(isInitialize: Boolean = false) {
        viewModel.getAllPeople(isInitialize)
    }

    private fun setupRecyclerView() {
        searchResultAdapter = CircleWithTitleAdapter()
        selectedPeopleAdapter = CircleWithTitleAdapter()
        popularPeopleAdapter = CircleWithTitleAdapter()

        val searchResultRecyclerViewLayout = GridLayoutManager(context, calcNumberOfColumns())
        val selectedPeopleRecyclerViewLayout = GridLayoutManager(context, calcNumberOfColumns())
        val popularRecyclerViewLayout = GridLayoutManager(context, calcNumberOfColumns())

        binding.fragmentSearchResultRecyclerview.apply {
            layoutManager = searchResultRecyclerViewLayout
            adapter = searchResultAdapter
        }

        binding.fragmentPeopleAndCompaniesActorsSelectedRecyclerview.apply {
            layoutManager = selectedPeopleRecyclerViewLayout
            adapter = selectedPeopleAdapter
        }

        binding.fragmentPeopleAndCompaniesPopularActorsRecyclerview.apply {
            layoutManager = popularRecyclerViewLayout
            adapter = popularPeopleAdapter
        }
        setOnLoadMoreListener()
        setSearchPeopleResultOnLoadMoreListener()
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
        launchWhenResumed {
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

        launchWhenResumed {
            viewModel.people.collectLatest { people ->
                popularPeopleAdapter.submitList(people)
            }
        }

        launchWhenResumed {
            viewModel.peopleFound.collectLatest { people ->
                if(isSearchInitialized) {
                    binding.fragmentSearchResultRecyclerview.visibility = View.VISIBLE
                    binding.fragmentPeopleSelectedParent.visibility = View.GONE
                    binding.fragmentPopularPeopleParent.visibility = View.GONE
                    searchResultAdapter.submitList(people)
                }
            }
        }
    }

    private fun setListeners() {
        binding.searchPeople.addTextChangedListener { editable ->
            lifecycleScope.launch {
                if(editable.toString().isBlank()) hideSearchResult()
                delay(2000)
                searchPeople(editable.toString())
            }
        }

        binding.searchPeople.setOnEditorActionListener { textView, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.searchPeople(textView.text.toString())
                val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(textView.windowToken, 0)
            }
            false
        }

        searchResultAdapter.onItemClick = { obj ->
            binding.searchPeople.setText("")
            hideSearchResult()
            when(obj) {
                is PersonModel -> {
                    viewModel.selectPerson(obj)
                }
            }
        }

        selectedPeopleAdapter.onItemClick = {
                obj ->
            when(obj) {
                is PersonModel -> {
                     viewModel.unselectPerson(obj)
                }
            }
        }

        popularPeopleAdapter.onItemClick = { obj ->
            when(obj) {
                is PersonModel -> {
                     viewModel.selectPerson(obj)
                }
            }
        }

        binding.filterButtonApply.setOnClickListener {
            (activity as FiltersActivity).saveChangesAndNavigateToPreviousActivity()
        }
    }

    private fun hideSearchResult() {
        isSearchInitialized = false
        binding.fragmentSearchResultRecyclerview.visibility = View.GONE
        binding.fragmentPeopleSelectedParent.visibility = View.VISIBLE
        binding.fragmentPopularPeopleParent.visibility = View.VISIBLE
    }

    private fun setSelectedPeople(people: List<PersonModel>) {
        selectedPeopleAdapter.submitList(people)
    }

    private fun setSearchPeopleResultOnLoadMoreListener() {
        binding.fragmentFilterPeopleAndCompaniesNestedscroll.setOnScrollChangeListener { nestedView, _, scrollY, _, oldScrollY ->
            val child = nestedView.findViewById<RecyclerView>(binding.fragmentSearchResultRecyclerview.id)
            if(child.isNotEmpty()) {
                if ((scrollY >= (child.measuredHeight - nestedView.measuredHeight)) &&
                    scrollY > oldScrollY && !viewModel.isSearchLoading) {
                    viewModel.loadMorePeople()
                }
            }
        }
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