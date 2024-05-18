package com.claudiogalvaodev.moviemanager.ui.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.claudiogalvaodev.moviemanager.databinding.FragmentFilterProviderBinding
import com.claudiogalvaodev.moviemanager.ui.filter.screens.FilterProviderScreen
import com.claudiogalvaodev.moviemanager.ui.menu.schedulednotifications.ui.theme.CineSeteTheme

class FilterProviderFragment: Fragment() {

    private val binding by lazy {
        FragmentFilterProviderBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.itemsList.setContent {
            CineSeteTheme {
                FilterProviderScreen()
            }
        }
    }


}