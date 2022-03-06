package com.claudiogalvaodev.moviemanager.ui.speciallist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.moviemanager.data.bd.entity.OscarNomination
import com.claudiogalvaodev.moviemanager.usecases.GetAllOscarNominationUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SpecialListViewModel(
    private val getAllOscarNominationUseCase: GetAllOscarNominationUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    private val _oscarNomination = MutableStateFlow<List<OscarNomination>>(mutableListOf())
    val oscarNomination = _oscarNomination.asStateFlow()

    init {
        getAllSpecialItemsSaved()
    }

    private fun getAllSpecialItemsSaved() = viewModelScope.launch(dispatcher) {
        getAllOscarNominationUseCase.invoke().collectLatest { allOscarNomination ->
            _oscarNomination.emit(allOscarNomination)
        }

    }

}