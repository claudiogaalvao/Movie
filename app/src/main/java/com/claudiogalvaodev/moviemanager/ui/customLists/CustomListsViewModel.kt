package com.claudiogalvaodev.moviemanager.ui.customLists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.moviemanager.ui.model.CustomListModel
import com.claudiogalvaodev.moviemanager.usecases.customlists.CreateNewCustomListUseCase
import com.claudiogalvaodev.moviemanager.usecases.customlists.GetAllCustomListsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CustomListsViewModel(
    private val createNewCustomListUseCase: CreateNewCustomListUseCase,
    private val getAllCustomListsUseCase: GetAllCustomListsUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _customLists = MutableStateFlow<List<CustomListModel>>(emptyList())
    val customLists = _customLists.asStateFlow()

    init {
        getAllCustomLists()
    }

    fun createNewList(listName: String) = viewModelScope.launch(dispatcher) {
        createNewCustomListUseCase(listName)
    }

    private fun getAllCustomLists() = viewModelScope.launch(dispatcher) {
        getAllCustomListsUseCase().collectLatest { customListsResult ->
            val allCustomLists = customListsResult.getOrNull()
            allCustomLists?.let {
                _customLists.emit(it)
            }
        }
    }

}