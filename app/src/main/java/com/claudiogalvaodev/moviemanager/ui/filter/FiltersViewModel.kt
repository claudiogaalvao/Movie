package com.claudiogalvaodev.moviemanager.ui.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.moviemanager.model.Genre
import com.claudiogalvaodev.moviemanager.ui.usecases.GetAllGenresUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FiltersViewModel(
    private val getAllGenresUseCase: GetAllGenresUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    private val _genres = MutableStateFlow<List<Genre>>(emptyList())
    val genres = _genres.asStateFlow()

    fun getAllGenres() = viewModelScope.launch {
        withContext(dispatcher) {
            val genresResult = getAllGenresUseCase.invoke()
            if(genresResult.isSuccess) {
                genresResult.getOrNull()?.let { allGenres ->
                    _genres.emit(allGenres)
                }
            }
        }
    }

}