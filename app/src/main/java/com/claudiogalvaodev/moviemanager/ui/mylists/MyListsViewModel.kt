package com.claudiogalvaodev.moviemanager.ui.mylists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.moviemanager.data.bd.entity.MyList
import com.claudiogalvaodev.moviemanager.ui.usecases.CreateNewListOnMyListsUseCase
import com.claudiogalvaodev.moviemanager.ui.usecases.GetAllMyListsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyListsViewModel(
    private val createNewListOnMyListsUseCase: CreateNewListOnMyListsUseCase,
    private val getAllMyListsUseCase: GetAllMyListsUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _myLists = MutableStateFlow<List<MyList>>(emptyList())
    val myLists = _myLists.asStateFlow()

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

}