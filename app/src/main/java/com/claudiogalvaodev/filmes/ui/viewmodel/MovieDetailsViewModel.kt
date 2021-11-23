package com.claudiogalvaodev.filmes.ui.viewmodel

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.filmes.model.FavoriteMovieEntity
import com.claudiogalvaodev.filmes.data.bd.entity.MovieEntity
import com.claudiogalvaodev.filmes.repository.MoviesRepository
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val repository: MoviesRepository
): ViewModel() {
    // TODO Como fazer o viewModel armazenar um estado (de movie) para não precisar ficar passando movie por parâmetro

    fun insertFavoriteMovie(movie: MovieEntity) = viewModelScope.launch {
        repository.insertFavoriteMovie(movie.id)
    }

    fun deleteFavoriteMovie(movie: MovieEntity) = viewModelScope.launch {
        repository.deleteFavoriteMovie(FavoriteMovieEntity(movie.id))
    }

    fun isFavorite(movie: MovieEntity) = repository.isFavorite(movie.id)
}