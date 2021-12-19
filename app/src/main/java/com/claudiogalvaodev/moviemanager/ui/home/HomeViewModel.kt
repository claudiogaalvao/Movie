package com.claudiogalvaodev.moviemanager.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.moviemanager.data.bd.entity.MovieEntity
import com.claudiogalvaodev.moviemanager.repository.MoviesRepository
import com.claudiogalvaodev.moviemanager.utils.format.formatDateUtils.orderMoviesByAscendingRelease
import com.claudiogalvaodev.moviemanager.utils.format.formatDateUtils.orderMoviesByDescendingRelease
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val repository: MoviesRepository
): ViewModel() {

    private val _trendingMovies = MutableLiveData<List<MovieEntity>>()
    val trendingMovies: LiveData<List<MovieEntity>>
        get() = _trendingMovies

    private val _upComingMovies = MutableLiveData<List<MovieEntity>>()
    val upComingMovies: LiveData<List<MovieEntity>>
        get() = _upComingMovies

    private val _latestMovies = MutableLiveData<List<MovieEntity>>()
    val latestMovies: LiveData<List<MovieEntity>>
        get() = _latestMovies

    fun getTrendingMovies() = viewModelScope.launch {
        // IO Possui threads reservadas
        // Default Compartilha uma mesma thread entre quem chamar esse tipo de dispatcher
        // Default mais recomendado para manipulações mais pesadas/longas
        withContext(Dispatchers.IO) {
            val moviesList = repository.getTrendingWeek()
            if(moviesList.isSuccess) {
                val filteredMovies = removeInvalidMovies(moviesList.getOrDefault(emptyList()))
                _trendingMovies.postValue(filteredMovies)
            }
        }
    }

    fun getUpComingMovies() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val moviesList = repository.getUpComing()
            if(moviesList.isSuccess) {
                val filteredMovies = removeInvalidMovies(moviesList.getOrDefault(emptyList()))
                val sortedMovies = orderMoviesByAscendingRelease(filteredMovies)
                _upComingMovies.postValue(sortedMovies)
            }
        }
    }

    fun getLatestMovies() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val moviesList = repository.getLatest()
            if(moviesList.isSuccess) {
                val filteredMovies = removeInvalidMovies(moviesList.getOrDefault(emptyList()))
                val sortedMovies = orderMoviesByDescendingRelease(filteredMovies)
                _latestMovies.postValue(sortedMovies)
            }
        }
    }

    private fun removeInvalidMovies(movies: List<MovieEntity>): List<MovieEntity> {
        return movies.filter { movie ->
            movie.poster_path != null || movie.backdrop_path != null
        }
    }

}