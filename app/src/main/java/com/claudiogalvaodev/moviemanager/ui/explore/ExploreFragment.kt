package com.claudiogalvaodev.moviemanager.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.claudiogalvaodev.moviemanager.databinding.FragmentExploreBinding
import com.claudiogalvaodev.moviemanager.model.Genre
import com.claudiogalvaodev.moviemanager.ui.explore.ExploreViewModel
import kotlinx.coroutines.flow.collectLatest
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllGenres()

        setObservables()
    }

    private fun setObservables() {
        lifecycleScope.launchWhenStarted {
            viewModel.genres.collectLatest { genres ->
                setupGenresRecyclerView(genres)
            }
        }
    }

    private fun setupGenresRecyclerView(genres: List<Genre>) {
        val adapter = GenresAdapter()
        binding.fragmentExploreRecyclerview.adapter = adapter
        adapter.submitList(genres)
    }

}