package com.claudiogalvaodev.moviemanager.ui.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.moviemanager.model.Movie
import com.claudiogalvaodev.moviemanager.ui.usecases.GetMoviesByCriteriousUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExploreMoviesViewModel(
    private val getMoviesByCriteriousUseCase: GetMoviesByCriteriousUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    private val _filteredMovies = MutableStateFlow<List<Movie>>(mutableListOf())
    val filteredMovies = _filteredMovies.asStateFlow()

    var isLoading: Boolean = false

    fun getMoviesByCriterious() = viewModelScope.launch {
        withContext(dispatcher) {
            isLoading = true
            val moviesResult = getMoviesByCriteriousUseCase.invoke()

            if(moviesResult.isSuccess) {
                moviesResult.getOrNull()?.let { movies ->
                    val moviesList = mutableListOf<Movie>()
                    moviesList.addAll(_filteredMovies.value)
                    moviesList.addAll(movies)
                    _filteredMovies.emit(moviesList)
                }
            }
            isLoading = false
        }
    }

}