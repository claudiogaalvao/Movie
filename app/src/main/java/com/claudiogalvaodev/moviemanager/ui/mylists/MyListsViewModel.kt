package com.claudiogalvaodev.moviemanager.ui.mylists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.moviemanager.data.bd.entity.MovieSaved
import com.claudiogalvaodev.moviemanager.data.bd.entity.MyList
import com.claudiogalvaodev.moviemanager.usecases.CreateNewListOnMyListsUseCase
import com.claudiogalvaodev.moviemanager.usecases.DeleteMyListUseCase
import com.claudiogalvaodev.moviemanager.usecases.GetAllMyListsUseCase
import com.claudiogalvaodev.moviemanager.usecases.GetMoviesByMyListIdUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyListsViewModel(
    private val createNewListOnMyListsUseCase: CreateNewListOnMyListsUseCase,
    private val getMoviesByMyListIdUseCase: GetMoviesByMyListIdUseCase,
    private val getAllMyListsUseCase: GetAllMyListsUseCase,
    private val deleteMyListUseCase: DeleteMyListUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _myLists = MutableStateFlow<List<MyList>>(emptyList())
    val myLists = _myLists.asStateFlow()

    private val _movies = MutableStateFlow<List<MovieSaved>>(emptyList())
    val movies = _movies.asStateFlow()

    fun createNewList(newList: MyList) = viewModelScope.launch {
        withContext(dispatcher) {
            createNewListOnMyListsUseCase.invoke(newList)
            getAllMyLists()
        }
    }

    fun getAllMyLists() = viewModelScope.launch {
        withContext(dispatcher) {
            getAllMyListsUseCase.invoke().collectLatest { allMyLists ->
                _myLists.emit(allMyLists)
            }
        }
    }

    fun getMoviesByMyListId(myListId: Int) = viewModelScope.launch {
        withContext(dispatcher) {
            getMoviesByMyListIdUseCase.invoke(myListId).collectLatest { movies ->
                _movies.emit(movies)
            }
        }
    }

    fun deleteMyList(myListId: Int) = viewModelScope.launch {
        withContext(dispatcher) {
            deleteMyListUseCase.invoke(myListId)
        }
    }

}