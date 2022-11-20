package com.claudiogalvaodev.moviemanager.ui.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.claudiogalvaodev.moviemanager.R
import com.claudiogalvaodev.moviemanager.databinding.FragmentFilterOrderbyBinding
import com.claudiogalvaodev.moviemanager.ui.filter.FiltersActivity.Companion.KEY_BUNDLE_CURRENT_VALUE
import com.claudiogalvaodev.moviemanager.utils.OrderByConstants

class FilterOrderByFragment: Fragment() {

    private val binding by lazy {
        FragmentFilterOrderbyBinding.inflate(layoutInflater)
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

        initBinding()
        setupListener()
    }

    private fun initBinding() {
        when(currentValue) {
            OrderByConstants.POPULARITY_DESC -> {
                binding.fragmentFilterOrderbyRadiogroup.check(R.id.filter_orderby_most_popular)
            }
            OrderByConstants.RELEASE_DATE_DESC -> {
                binding.fragmentFilterOrderbyRadiogroup.check(R.id.filter_orderby_latest_release)
            }
            OrderByConstants.VOTE_AVERAGE_DESC -> {
                binding.fragmentFilterOrderbyRadiogroup.check(R.id.filter_orderby_highest_rating)
            }
            OrderByConstants.REVENUE_DESC -> {
                binding.fragmentFilterOrderbyRadiogroup.check(R.id.filter_orderby_highest_revenue)
            }
        }
    }

    private fun setupListener() {
        binding.fragmentFilterOrderbyRadiogroup.setOnCheckedChangeListener { _, radioButtonId ->
            val parent = (activity as FiltersActivity)
            when(radioButtonId) {
                R.id.filter_orderby_most_popular -> parent.changeCurrentValue(OrderByConstants.POPULARITY_DESC)
                R.id.filter_orderby_highest_rating -> parent.changeCurrentValue(OrderByConstants.VOTE_AVERAGE_DESC)
                R.id.filter_orderby_latest_release -> parent.changeCurrentValue(OrderByConstants.RELEASE_DATE_DESC)
                R.id.filter_orderby_highest_revenue -> parent.changeCurrentValue(OrderByConstants.REVENUE_DESC)
            }
        }

        binding.filterButtonApply.setOnClickListener {
            (activity as FiltersActivity).saveChangesAndNavigateToPreviousActivity()
        }
    }

}