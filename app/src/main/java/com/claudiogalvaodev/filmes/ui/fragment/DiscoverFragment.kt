package com.claudiogalvaodev.filmes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.claudiogalvaodev.filmes.data.bd.entity.MovieEntity
import com.claudiogalvaodev.filmes.databinding.FragmentDiscoverBinding
import com.claudiogalvaodev.filmes.ui.adapter.PrincipalMoviesAdapter
import com.claudiogalvaodev.filmes.ui.viewmodel.FavoriteViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class DiscoverFragment: Fragment() {
    private val viewModel: FavoriteViewModel by viewModel()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservables()
    }

    private fun initObservables() {
        viewModel.favoriteMovies.observe(viewLifecycleOwner) { movies ->
            configuraLista(movies)
        }
    }

    private fun configuraLista(movies: List<MovieEntity>) {
        binding.favoriteMoviesRecyclerview.apply {
            adapter = PrincipalMoviesAdapter(movies)
            layoutManager = GridLayoutManager(context, 2)
        }
    }
}