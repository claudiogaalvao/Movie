package com.claudiogalvaodev.moviemanager.ui.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.moviemanager.ui.model.GenreModel
import com.claudiogalvaodev.moviemanager.ui.model.PersonModel
import com.claudiogalvaodev.moviemanager.usecases.movies.GetAllGenresUseCase
import com.claudiogalvaodev.moviemanager.usecases.movies.GetAllPeopleUseCase
import com.claudiogalvaodev.moviemanager.usecases.movies.SearchPeopleUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FiltersViewModel(
    private val getAllPeopleUseCase: GetAllPeopleUseCase,
    private val getAllGenresUseCase: GetAllGenresUseCase,
    private val searchPeopleUseCase: SearchPeopleUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    private val _genres = MutableStateFlow<List<GenreModel>>(emptyList())
    val genres = _genres.asStateFlow()

    private val _people = MutableStateFlow<List<PersonModel>>(emptyList())
    val people = _people.asStateFlow()

    private val _peopleSelected = MutableStateFlow<List<PersonModel>>(emptyList())
    val peopleSelected = _peopleSelected.asStateFlow()

    private val _peopleFound = MutableStateFlow<List<PersonModel>>(mutableListOf())
    val peopleFound = _peopleFound.asStateFlow()

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

    fun initPeoplePreviousSelected(peoplePreviousSelected: List<PersonModel>) = viewModelScope.launch {
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
                    val peopleList = mutableListOf<PersonModel>()
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

    fun selectPerson(personSelected: PersonModel) = viewModelScope.launch {
        withContext(dispatcher) {
            val allPeople = people.value.toMutableList()
            val selectedPeople = mutableListOf<PersonModel>()
            selectedPeople.addAll(_peopleSelected.value)

            personSelected.position = allPeople.indexOf(personSelected)
            selectedPeople.add(personSelected)

            allPeople.remove(personSelected)
            _peopleSelected.emit(selectedPeople)
            _people.emit(allPeople)
        }
    }

    fun unselectPerson(person: PersonModel) = viewModelScope.launch {
        withContext(dispatcher) {
            val allPeople = people.value.toMutableList()
            val selectedPeople = mutableListOf<PersonModel>()
            selectedPeople.addAll(_peopleSelected.value)
            selectedPeople.remove(person)

            person.position.let { position ->
                if(position >= 0 && allPeople.size > position) {
                    allPeople.add(position, person)
                } else {
                    allPeople.add(person)
                }
                _peopleSelected.emit(selectedPeople)
                _people.emit(allPeople)
            }
        }
    }

    // Search people
    private var lastQuery = ""

    var isSearchLoading: Boolean = false
    var isNewSearch: Boolean = true
    var getSearchResultSecondPage: Boolean = true

    fun searchPeople(query: String) = viewModelScope.launch {
        withContext(dispatcher) {
            if(isNewSearch) {
                if(query == lastQuery) return@withContext
                lastQuery = query
            }

            isSearchLoading = true
            val peopleResult = searchPeopleUseCase.invoke(query, isNewSearch)

            if(getSearchResultSecondPage && _peopleFound.value.isNotEmpty()) getSearchResultSecondPage = false

            if(peopleResult.isSuccess) {
                peopleResult.getOrNull()?.let { people ->
                    val peopleList = mutableListOf<PersonModel>()
                    peopleList.addAll(_peopleFound.value)
                    peopleList.addAll(people.filter { movie -> !(_peopleSelected.value.contains(movie)) })
                    _peopleFound.emit(peopleList)
                    isSearchLoading = false
                }
                isSearchLoading = false
                isNewSearch = true
            }
        }
    }

    fun loadMorePeople() {
        isNewSearch = false
        searchPeople(lastQuery)
    }

}