package com.claudiogalvaodev.filmes.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.filmes.data.bd.entity.MovieEntity
import com.claudiogalvaodev.filmes.model.FavoriteMovieEntity
import com.claudiogalvaodev.filmes.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailsViewModel(
    private val repository: MoviesRepository
): ViewModel() {

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean>
        get() = _isFavorite

    fun insertFavoriteMovie(movie: MovieEntity) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.insertFavoriteMovie(movie.id)
        }
    }

    fun deleteFavoriteMovie(movie: MovieEntity) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.deleteFavoriteMovie(FavoriteMovieEntity(movie.id))
        }
    }

    fun isFavorite(movie: MovieEntity) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.getFavoriteMovieById(movie.id).collect { favoriteMovie ->
                if(favoriteMovie != null) {
                    _isFavorite.postValue(true)
                } else {
                    _isFavorite.postValue(false)
                }
            }

        }
    }
}