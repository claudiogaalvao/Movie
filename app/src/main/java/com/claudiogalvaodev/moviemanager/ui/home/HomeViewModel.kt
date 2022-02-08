package com.claudiogalvaodev.moviemanager.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.moviemanager.data.model.Movie
import com.claudiogalvaodev.moviemanager.usecases.GetTrendingWeekMoviesUseCase
import com.claudiogalvaodev.moviemanager.usecases.GetUpComingAndPlayingNowMoviesUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val getTrendingWeekMoviesUseCase: GetTrendingWeekMoviesUseCase,
    private val getUpComingAndPlayingNowMoviesUseCase: GetUpComingAndPlayingNowMoviesUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    private val _trendingMovies = MutableStateFlow<List<Movie>>(emptyList())
    val trendingMovies = _trendingMovies.asStateFlow()

    private val _upComingMovies = MutableStateFlow<List<Movie>>(emptyList())
    val upComingMovies = _upComingMovies.asStateFlow()

    private val _playingNowMovies = MutableStateFlow<List<Movie>>(emptyList())
    val playingNowMovies = _playingNowMovies.asStateFlow()

    fun getTrendingMovies() = viewModelScope.launch {
        withContext(dispatcher) {
            val moviesList = getTrendingWeekMoviesUseCase.invoke()
            if(moviesList.isSuccess) {
                _trendingMovies.emit(moviesList.getOrDefault(emptyList()))
            }
        }
    }

    fun getUpComingAndPlayingNow() = viewModelScope.launch {
        getUpComingAndPlayingNowMoviesUseCase.invoke()

        _upComingMovies.emit(getUpComingAndPlayingNowMoviesUseCase.upComingMovies.value)
        _playingNowMovies.emit(getUpComingAndPlayingNowMoviesUseCase.playingNowMovies.value)
    }

}