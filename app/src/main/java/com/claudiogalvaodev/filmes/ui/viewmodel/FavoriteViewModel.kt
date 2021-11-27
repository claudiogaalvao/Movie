package com.claudiogalvaodev.filmes.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.filmes.data.bd.entity.MovieEntity
import com.claudiogalvaodev.filmes.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteViewModel(
    private val repository: MoviesRepository
): ViewModel() {

    private val _favoriteMovies = MutableLiveData<List<MovieEntity>>()
    val favoriteMovies: LiveData<List<MovieEntity>>
        get() = _favoriteMovies

    init {
        getFavoriteMovies()
    }

    fun getFavoriteMovies() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val favoriteMoviesResult = repository.getFavoriteMovies()
            favoriteMoviesResult.collect { movies ->
                _favoriteMovies.postValue(movies)
            }
        }
    }
}