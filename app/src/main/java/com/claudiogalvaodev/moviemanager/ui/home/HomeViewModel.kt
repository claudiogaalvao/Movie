package com.claudiogalvaodev.moviemanager.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.moviemanager.data.bd.entity.MovieEntity
import com.claudiogalvaodev.moviemanager.repository.MoviesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val repository: MoviesRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    private val _trendingMovies = MutableStateFlow<List<MovieEntity>>(emptyList())
    val trendingMovies = _trendingMovies.asStateFlow()

    private val _upComingMovies = MutableStateFlow<List<MovieEntity>>(emptyList())
    val upComingMovies = _upComingMovies.asStateFlow()

    private val _playingNowMovies = MutableStateFlow<List<MovieEntity>>(emptyList())
    val playingNowMovies = _playingNowMovies.asStateFlow()

    fun getTrendingMovies() = viewModelScope.launch {
        // IO Possui threads reservadas
        // Default Compartilha uma mesma thread entre quem chamar esse tipo de dispatcher
        // Default mais recomendado para manipulações mais pesadas/longas
        withContext(dispatcher) {
            val moviesList = repository.getTrendingWeek()
            if(moviesList.isSuccess) {
                _trendingMovies.emit(moviesList.getOrDefault(emptyList()))
            }
        }
    }

    fun getUpComingMovies() = viewModelScope.launch {
        withContext(dispatcher) {
            val moviesList = repository.getUpComing()
            if(moviesList.isSuccess) {
                _upComingMovies.emit(moviesList.getOrDefault(emptyList()))
            }
        }
    }

    fun getPlayingNowMovies() = viewModelScope.launch {
        withContext(dispatcher) {
            val moviesList = repository.getPlayingNow()
            if(moviesList.isSuccess) {
                _playingNowMovies.emit(moviesList.getOrDefault(emptyList()))
            }
        }
    }

}