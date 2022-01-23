package com.claudiogalvaodev.moviemanager.ui.filter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import org.koin.android.viewmodel.ext.android.viewModel
import com.claudiogalvaodev.moviemanager.R
import com.claudiogalvaodev.moviemanager.databinding.FragmentFilterGenreBinding
import com.claudiogalvaodev.moviemanager.databinding.ItemRadioButtonBinding
import com.claudiogalvaodev.moviemanager.ui.filter.FiltersActivity.Companion.KEY_BUNDLE_CURRENT_VALUE
import com.claudiogalvaodev.moviemanager.utils.OrderByConstants
import kotlinx.coroutines.flow.collectLatest

class FilterGenresFragment: Fragment() {

    private val viewModel: FiltersViewModel by viewModel()
    private val binding by lazy {
        FragmentFilterGenreBinding.inflate(layoutInflater)
    }

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
        getGenres()
        setObservables()
        setupListener()
    }

    private fun setTitle() {
        (activity as FiltersActivity)
            .setToolbarTitle(resources.getString(R.string.fragment_genres_title))
    }

    private fun getGenres() {
        viewModel.getAllGenres()
    }

    private fun setObservables() {
        lifecycleScope.launchWhenStarted {
            viewModel.genres.collectLatest { genres ->
                for(genre in genres) {
                    val newRadioButton = createRadioButton(genre.id, genre.name)
                    binding.fragmentFilterGenreRadiogroup.addView(newRadioButton)
                    if(currentValue == genre.id.toString()) {
                        binding.fragmentFilterGenreRadiogroup.check(newRadioButton.id)
                    }
                }
            }
        }
    }

    private fun createRadioButton(id: Int, text: String): RadioButton {
        val radioButtonBinding = ItemRadioButtonBinding.inflate(layoutInflater)
        val radioButton = radioButtonBinding.filterRadioButton
        radioButton.id = id
        radioButton.text = text
        if (radioButton.parent != null) (radioButton.parent as ViewGroup).removeView(radioButton)
        return radioButton
    }

    private fun setupListener() {
        binding.fragmentFilterGenreRadiogroup.setOnCheckedChangeListener { _, radioButtonId ->
            val parent = (activity as FiltersActivity)
            parent.changeCurrentValue(radioButtonId.toString())
        }

        binding.filterButtonApply.setOnClickListener {
            (activity as FiltersActivity).checkAndNavigateToPreviousActivity()
        }
    }

}