package com.claudiogalvaodev.moviemanager.ui.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.moviemanager.model.Genre
import com.claudiogalvaodev.moviemanager.repository.MoviesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExploreViewModel(
    private val repository: MoviesRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    private val _genres = MutableStateFlow<List<Genre>>(emptyList())
    val genres = _genres.asStateFlow()

    fun getAllGenres() = viewModelScope.launch {
        withContext(dispatcher) {
            val genresResult = repository.getAllGenres()
            if(genresResult.isSuccess) {
                _genres.emit(genresResult.getOrDefault(emptyList()))
            }
        }
    }

}