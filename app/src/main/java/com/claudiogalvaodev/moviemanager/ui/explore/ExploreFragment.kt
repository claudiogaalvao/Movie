package com.claudiogalvaodev.moviemanager.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.claudiogalvaodev.moviemanager.databinding.FragmentExploreBinding
import com.claudiogalvaodev.moviemanager.ui.explore.ExploreViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class ExploreFragment: Fragment() {
    private val viewModel: ExploreViewModel by viewModel()
    private val binding by lazy {
        FragmentExploreBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

}