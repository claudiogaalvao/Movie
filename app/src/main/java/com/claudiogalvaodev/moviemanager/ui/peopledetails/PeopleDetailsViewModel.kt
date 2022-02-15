package com.claudiogalvaodev.moviemanager.ui.peopledetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.moviemanager.data.model.Employe
import com.claudiogalvaodev.moviemanager.data.model.Filter
import com.claudiogalvaodev.moviemanager.data.model.Movie
import com.claudiogalvaodev.moviemanager.usecases.GetMoviesByCriteriousUseCase
import com.claudiogalvaodev.moviemanager.usecases.GetPersonDetailsUseCase
import com.claudiogalvaodev.moviemanager.utils.OrderByConstants
import com.claudiogalvaodev.moviemanager.utils.enums.FilterType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PeopleDetailsViewModel(
    private val getMoviesByCriteriousUseCase: GetMoviesByCriteriousUseCase,
    private val getPersonDetailsUseCase: GetPersonDetailsUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    private val _personDetails = MutableStateFlow<Employe?>(null)
    val personDetails = _personDetails.asStateFlow()

    private val _movies = MutableStateFlow<List<Movie>>(mutableListOf())
    val movies = _movies.asStateFlow()

    var isMoviesLoading: Boolean = false
    var isUpdate: Boolean = false
    var getSecondPage: Boolean = false

    private var theresNoMoreMovies = false

    private fun getFilter(personId: String): List<Filter> {
        val filters: MutableList<Filter> = mutableListOf()
        filters.add(Filter(type = FilterType.SORT_BY, name = "", currentValue = OrderByConstants.POPULARITY_DESC))
        filters.add(Filter(type = FilterType.PEOPLE, name = "", currentValue = personId))
        return filters
    }

    fun getPersonDetails(personId: String) = viewModelScope.launch {
        withContext(dispatcher) {
            val personDetailsResult = getPersonDetailsUseCase.invoke(personId)

            if(personDetailsResult.isSuccess) {
                personDetailsResult.getOrNull()?.let { person ->
                    _personDetails.emit(person)
                }
            }
        }
    }

    fun getMovies(personId: String) = viewModelScope.launch {
        withContext(dispatcher) {
            isMoviesLoading = true
            val moviesResult = if(!theresNoMoreMovies) {
                getMoviesByCriteriousUseCase.invoke(getFilter(personId), isUpdate)
            } else {
                Result.failure(Exception())
            }

            if(getSecondPage && _movies.value.isNotEmpty()) getSecondPage = false

            if(moviesResult.isSuccess) {
                moviesResult.getOrNull()?.let { movies ->
                    if(movies.isEmpty()) {
                        theresNoMoreMovies = true
                        return@withContext
                    }
                    if(isUpdate) {
                        _movies.emit(movies)
                        isMoviesLoading = false
                        isUpdate = false
                        return@withContext
                    }
                    val moviesList = mutableListOf<Movie>()
                    moviesList.addAll(_movies.value)
                    moviesList.addAll(movies)
                    _movies.emit(moviesList)
                }
            }
            isMoviesLoading = false
            isUpdate = false
        }
    }

}