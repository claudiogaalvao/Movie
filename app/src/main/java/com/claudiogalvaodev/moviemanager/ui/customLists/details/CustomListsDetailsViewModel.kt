package com.claudiogalvaodev.moviemanager.ui.customLists.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.moviemanager.ui.model.MovieModel
import com.claudiogalvaodev.moviemanager.usecases.customlists.DeleteCustomListUseCase
import com.claudiogalvaodev.moviemanager.usecases.customlists.GetMoviesByListIdUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CustomListsDetailsViewModel(
    private val customListId: Int,
    private val getMoviesByListIdUseCase: GetMoviesByListIdUseCase,
    private val deleteCustomListUseCase: DeleteCustomListUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _movies = MutableStateFlow<List<MovieModel>>(emptyList())
    val movies = _movies.asStateFlow()

    init {
        getMoviesByListId()
    }

    private fun getMoviesByListId() = viewModelScope.launch(dispatcher) {
        getMoviesByListIdUseCase(customListId).collectLatest { moviesResult ->
            val movies = moviesResult.getOrNull()
            movies?.let {
                _movies.emit(it)
            }
        }
    }

    fun deleteMyList() = viewModelScope.launch(dispatcher) {
        deleteCustomListUseCase.invoke(customListId)
    }

}