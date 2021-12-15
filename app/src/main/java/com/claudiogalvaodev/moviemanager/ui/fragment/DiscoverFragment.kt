package com.claudiogalvaodev.moviemanager.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.claudiogalvaodev.moviemanager.databinding.FragmentDiscoverBinding
import com.claudiogalvaodev.moviemanager.ui.viewmodel.DiscoverViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class DiscoverFragment: Fragment() {
    private val viewModel: DiscoverViewModel by viewModel()
    private val binding by lazy {
        FragmentDiscoverBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

}