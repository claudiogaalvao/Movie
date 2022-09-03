package com.claudiogalvaodev.moviemanager.ui.menu.customLists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.moviemanager.ui.model.CustomListModel
import com.claudiogalvaodev.moviemanager.ui.model.MovieModel
import com.claudiogalvaodev.moviemanager.usecases.customlists.CreateNewCustomListUseCase
import com.claudiogalvaodev.moviemanager.usecases.customlists.DeleteCustomListUseCase
import com.claudiogalvaodev.moviemanager.usecases.customlists.GetAllCustomListsUseCase
import com.claudiogalvaodev.moviemanager.usecases.customlists.GetMoviesByListIdUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CustomListsViewModel(
    private val createNewCustomListUseCase: CreateNewCustomListUseCase,
    private val getMoviesByListIdUseCase: GetMoviesByListIdUseCase,
    private val getAllCustomListsUseCase: GetAllCustomListsUseCase,
    private val deleteCustomListUseCase: DeleteCustomListUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _customLists = MutableStateFlow<List<CustomListModel>>(emptyList())
    val customLists = _customLists.asStateFlow()

    private val _movies = MutableStateFlow<List<MovieModel>>(emptyList())
    val movies = _movies.asStateFlow()

    fun createNewList(listName: String) = viewModelScope.launch(dispatcher) {
        createNewCustomListUseCase.invoke(listName)
        getAllCustomLists()
    }

    fun getAllCustomLists() = viewModelScope.launch(dispatcher) {
        val allCustomListsResult = getAllCustomListsUseCase.invoke()
        if (allCustomListsResult.isSuccess) {
            val allCustomLists = allCustomListsResult.getOrNull()
            allCustomLists?.let {
                _customLists.emit(it)
            }
        }
    }

    fun getMoviesByListId(myListId: Int) = viewModelScope.launch(dispatcher) {
        val moviesResult = getMoviesByListIdUseCase.invoke(myListId)
        if (moviesResult.isSuccess) {
            val movies = moviesResult.getOrNull()
            movies?.let {
                _movies.emit(it)
            }
        }
    }

    fun deleteMyList(myListId: Int) = viewModelScope.launch(dispatcher) {
        deleteCustomListUseCase.invoke(myListId)
    }

}