package com.claudiogalvaodev.moviemanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.claudiogalvaodev.moviemanager.databinding.FragmentFilterRuntimeBinding
import com.claudiogalvaodev.moviemanager.ui.filter.FiltersActivity
import com.claudiogalvaodev.moviemanager.utils.RuntimeConstants

class FilterRuntimeFragment : Fragment() {

    private val binding by lazy {
        FragmentFilterRuntimeBinding.inflate(layoutInflater)
    }

    private lateinit var currentValue: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentValue = arguments?.getString(FiltersActivity.KEY_BUNDLE_CURRENT_VALUE).orEmpty()

        setTitle()
        initBinding()
        setupListener()
    }

    private fun setTitle() {
        (activity as FiltersActivity)
            .setToolbarTitle(resources.getString(R.string.fragment_filter_runtime_title))
    }

    private fun initBinding() {
        when(currentValue) {
            RuntimeConstants.SHORT -> {
                binding.fragmentFilterRuntimeRadiogroup.check(R.id.filter_runtime_short)
            }
            RuntimeConstants.MEDIUM -> {
                binding.fragmentFilterRuntimeRadiogroup.check(R.id.filter_runtime_medium)
            }
            RuntimeConstants.LONG_DEFAULT -> {
                binding.fragmentFilterRuntimeRadiogroup.check(R.id.filter_runtime_long_default)
            }
            RuntimeConstants.LONGEST -> {
                binding.fragmentFilterRuntimeRadiogroup.check(R.id.filter_runtime_longest)
            }
        }
    }

    private fun setupListener() {
        binding.fragmentFilterRuntimeRadiogroup.setOnCheckedChangeListener { _, radioButtonId ->
            val parent = (activity as FiltersActivity)
            when(radioButtonId) {
                R.id.filter_runtime_short -> parent.changeCurrentValue(RuntimeConstants.SHORT)
                R.id.filter_runtime_medium -> parent.changeCurrentValue(RuntimeConstants.MEDIUM)
                R.id.filter_runtime_long_default -> parent.changeCurrentValue(RuntimeConstants.LONG_DEFAULT)
                R.id.filter_runtime_longest -> parent.changeCurrentValue(RuntimeConstants.LONGEST)
            }
        }

        binding.filterButtonApply.setOnClickListener {
            (activity as FiltersActivity).checkAndNavigateToPreviousActivity()
        }
    }

}