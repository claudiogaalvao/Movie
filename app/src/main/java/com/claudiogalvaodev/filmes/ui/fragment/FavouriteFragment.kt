package com.claudiogalvaodev.filmes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.claudiogalvaodev.filmes.databinding.FavoriteMoviesBinding
import com.claudiogalvaodev.filmes.data.bd.entity.MovieEntity
import com.claudiogalvaodev.filmes.ui.adapter.MovieListAdapter
import com.claudiogalvaodev.filmes.ui.viewmodel.FavoriteViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class FavouriteFragment: Fragment() {
    private val viewModel: FavoriteViewModel by viewModel()
    private val binding by lazy {
        FavoriteMoviesBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    // TODO Tem um jeito melhor de capturar os favoritos sem fazer a chamada a todo momento?
    override fun onResume() {
        super.onResume()
        getFavoriteMovies()
    }

    private fun getFavoriteMovies() {
        viewModel.getFavoriteMovies().observe(viewLifecycleOwner) {
            it.let { result ->
                when(result) {
                    is com.claudiogalvaodev.filmes.repository.Result.Success -> {
                        result.data.let { listaFilmes ->
                            if(listaFilmes != null) configuraLista(listaFilmes)
                        }
                    }
                    is com.claudiogalvaodev.filmes.repository.Result.Error -> {
                        Toast.makeText(context, "Não há filmes para mostrar", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun configuraLista(movies: List<MovieEntity>) {
        binding.favoriteMoviesRecyclerview.apply {
            adapter = MovieListAdapter(movies)
            layoutManager = GridLayoutManager(context, 2)
        }
    }
}