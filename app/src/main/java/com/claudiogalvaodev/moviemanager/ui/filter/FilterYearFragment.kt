package com.claudiogalvaodev.moviemanager.ui.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import com.claudiogalvaodev.moviemanager.R
import com.claudiogalvaodev.moviemanager.databinding.FragmentFilterYearBinding
import com.claudiogalvaodev.moviemanager.databinding.ItemRadioButtonBinding
import com.claudiogalvaodev.moviemanager.ui.filter.FiltersActivity.Companion.KEY_BUNDLE_CURRENT_VALUE
import java.time.LocalDate

class FilterYearFragment: Fragment() {

    private val binding by lazy {
        FragmentFilterYearBinding.inflate(layoutInflater)
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
        setItemsToRadioGroup()
        setupListener()
    }

    private fun setTitle() {
        (activity as FiltersActivity)
            .setToolbarTitle(resources.getString(R.string.filter_type_years))
    }

    private fun setItemsToRadioGroup() {
        val currentYear = LocalDate.now().year
        for(year in currentYear downTo 1930) {
            val newRadioButton = createRadioButton(year, year.toString())
            binding.fragmentFilterYearRadiogroup.addView(newRadioButton)
            if(currentValue == year.toString()) {
                binding.fragmentFilterYearRadiogroup.check(newRadioButton.id)
            }
        }
    }

    private fun createRadioButton(id: Int, text: String): RadioButton {
        val radioButtonBinding = ItemRadioButtonBinding.inflate(layoutInflater)
        val radioButton = radioButtonBinding.filterRadioButton
        radioButton.id = id
        radioButton.text = text
        radioButton.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
        if (radioButton.parent != null) (radioButton.parent as ViewGroup).removeView(radioButton)
        return radioButton
    }

    private fun setupListener() {
        binding.fragmentFilterYearRadiogroup.setOnCheckedChangeListener { _, radioButtonId ->
            val parent = (activity as FiltersActivity)
            parent.changeCurrentValue(radioButtonId.toString())
        }

        binding.filterButtonApply.setOnClickListener {
            (activity as FiltersActivity).saveChangesAndNavigateToPreviousActivity()
        }
    }

}