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

    private val _peopleSelected = MutableStateFlow<List<Employe>>(emptyList())
    val peopleSelected = _peopleSelected.asStateFlow()

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

    fun initPeoplePreviousSelected(peoplePreviousSelected: List<Employe>) = viewModelScope.launch {
        withContext(dispatcher) {
            _peopleSelected.emit(peoplePreviousSelected)
        }
    }

    fun getAllPeople(isInitialize: Boolean) = viewModelScope.launch {
        withContext(dispatcher) {
            isLoadingActors = true
            val peopleResult = getAllPeopleUseCase.invoke(isInitialize)
            if(peopleResult.isSuccess) {
                peopleResult.getOrNull()?.let { allPeople ->
                    val peopleList = mutableListOf<Employe>()
                    peopleList.addAll(_people.value)

                    val allPeopleFiltered = allPeople.toMutableList()
                    allPeopleFiltered.removeAll(_peopleSelected.value)

                    peopleList.addAll(allPeopleFiltered)
                    _people.emit(peopleList)
                }
                isLoadingActors = false
            }
        }
    }

    fun selectPerson(personSelected: Employe) = viewModelScope.launch {
        withContext(dispatcher) {
            val allPeople = people.value.toMutableList()
            val selectedPeople = mutableListOf<Employe>()
            selectedPeople.addAll(_peopleSelected.value)

            personSelected.position = allPeople.indexOf(personSelected)
            selectedPeople.add(personSelected)

            allPeople.remove(personSelected)
            _peopleSelected.emit(selectedPeople)
            _people.emit(allPeople)
        }
    }

    fun unselectPerson(person: Employe) = viewModelScope.launch {
        withContext(dispatcher) {
            val allPeople = people.value.toMutableList()
            val selectedPeople = mutableListOf<Employe>()
            selectedPeople.addAll(_peopleSelected.value)
            selectedPeople.remove(person)

            val position = person.position
            if(position != null && allPeople.size > position) {
                allPeople.add(position, person)
            } else {
                allPeople.add(person)
            }
            _peopleSelected.emit(selectedPeople)
            _people.emit(allPeople)
        }
    }

}