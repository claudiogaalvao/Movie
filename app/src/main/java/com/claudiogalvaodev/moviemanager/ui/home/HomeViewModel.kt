package com.claudiogalvaodev.moviemanager.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.moviemanager.model.Movie
import com.claudiogalvaodev.moviemanager.repository.MoviesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val repository: MoviesRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    private val _trendingMovies = MutableStateFlow<List<Movie>>(emptyList())
    val trendingMovies = _trendingMovies.asStateFlow()

    private val _upComingMovies = MutableStateFlow<List<Movie>>(emptyList())
    val upComingMovies = _upComingMovies.asStateFlow()

    private val _playingNowMovies = MutableStateFlow<List<Movie>>(emptyList())
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

    fun getUpComingAndPlayingNow() {

        viewModelScope.launch {
            repository.updateUpComingAndPlayingNow()

            repository.upComingMovies.collectLatest { movies ->
                _upComingMovies.emit(movies)
            }
        }

        viewModelScope.launch {
            repository.playingNowMovies.collectLatest { movies ->
                _playingNowMovies.emit(movies)
            }
        }

    }

}