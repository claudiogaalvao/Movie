package com.claudiogalvaodev.filmes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.claudiogalvaodev.filmes.databinding.MovieListBinding
import com.claudiogalvaodev.filmes.data.bd.entity.MovieEntity
import com.claudiogalvaodev.filmes.ui.adapter.MovieListAdapter
import com.claudiogalvaodev.filmes.ui.viewmodel.HomeViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment: Fragment() {
    private val viewModel: HomeViewModel by viewModel()
    private val binding by lazy {
        MovieListBinding.inflate(layoutInflater)
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
        // TODO Porque a lista volta ao topo ao navegar pelo bottomNavigator?
        getPopularMovies()
    }

    private fun getPopularMovies() {
        viewModel.getPopularMovies().observe(viewLifecycleOwner) {
            it.let { result ->
                when(result) {
                    is com.claudiogalvaodev.filmes.repository.Result.Success -> {
                        result.data.let { listaFilmes ->
                            if(listaFilmes != null) configuraLista(listaFilmes)
                        }
                    }
                    is com.claudiogalvaodev.filmes.repository.Result.Error -> {
                        Toast.makeText(context, "Erro ao buscar filmes", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun configuraLista(movies: List<MovieEntity>) {
        binding.movieListRecyclerview.apply {
            adapter = MovieListAdapter(movies)
            layoutManager = GridLayoutManager(context, 2)
        }
    }
}