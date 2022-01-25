package com.claudiogalvaodev.moviemanager.ui.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.moviemanager.model.Employe
import com.claudiogalvaodev.moviemanager.model.Genre
import com.claudiogalvaodev.moviemanager.ui.usecases.GetAllGenresUseCase
import com.claudiogalvaodev.moviemanager.ui.usecases.GetAllPeopleUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FiltersViewModel(
    private val getAllPeopleUseCase: GetAllPeopleUseCase,
    private val getAllGenresUseCase: GetAllGenresUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    private val _genres = MutableStateFlow<List<Genre>>(emptyList())
    val genres = _genres.asStateFlow()

    private val _people = MutableStateFlow<List<Employe>>(emptyList())
    val people = _people.asStateFlow()

    var isLoadingActors: Boolean = false

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

    fun getAllPeople() = viewModelScope.launch {
        withContext(dispatcher) {
            isLoadingActors = true
            val peopleResult = getAllPeopleUseCase.invoke()
            if(peopleResult.isSuccess) {
                peopleResult.getOrNull()?.let { allPeople ->
                    val peopleList = mutableListOf<Employe>()
                    peopleList.addAll(_people.value)
                    peopleList.addAll(allPeople)
                    _people.emit(peopleList)
                }
            }
            isLoadingActors = false
        }
    }

}