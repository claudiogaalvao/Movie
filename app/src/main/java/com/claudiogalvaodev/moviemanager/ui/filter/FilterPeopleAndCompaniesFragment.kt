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
import android.view.animation.TranslateAnimation




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
        getPeople(true)
        setupRecyclerView()
        setObservables()
        setListeners()
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
        binding.fragmentFilterPeopleAndCompaniesNestedscroll.setOnScrollChangeListener { nestedView, scrollX, scrollY, oldScrollX, oldScrollY ->
            val child = nestedView.findViewById<RecyclerView>(R.id.fragment_people_and_companies_popular_actors_recyclerview)
            if(child.isNotEmpty()) {
                if ((scrollY >= (child.measuredHeight - nestedView.measuredHeight)) &&
                    scrollY > oldScrollY && !viewModel.isLoadingActors) {
                    getPeople()
                }
            }

            if((scrollY > oldScrollY) && binding.filterButtonApply.isShown) {
                slideDown(binding.filterButtonParent)
            } else if(scrollY < oldScrollY && !binding.filterButtonApply.isShown) {
                slideUp(binding.filterButtonParent)
            }
        }
    }

    private fun setObservables() {
        lifecycleScope.launchWhenStarted {
            viewModel.peopleSelected.collectLatest { people ->
                setSelectedPeople(people)
                (activity as FiltersActivity).changeCurrentValue(viewModel.generatePeopleSelectedConcatened(people))
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
            (activity as FiltersActivity).checkAndNavigateToPreviousActivity()
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

    // slide the view from below itself to the current position
    fun slideUp(view: View) {
        view.visibility = View.VISIBLE
        val animate = TranslateAnimation(
            0F,  // fromXDelta
            0F,  // toXDelta
            view.height.toFloat(),  // fromYDelta
            0F
        ) // toYDelta
        animate.duration = 500
        animate.fillAfter = true
        view.startAnimation(animate)
        view.visibility = View.VISIBLE

    }

    // slide the view from its current position to below itself
    fun slideDown(view: View) {
        val animate = TranslateAnimation(
            0F,  // fromXDelta
            0F,  // toXDelta
            0F,  // fromYDelta
            view.height.toFloat()
        ) // toYDelta
        animate.duration = 500
        animate.fillAfter = true
        view.startAnimation(animate)
        view.visibility = View.GONE
    }

}