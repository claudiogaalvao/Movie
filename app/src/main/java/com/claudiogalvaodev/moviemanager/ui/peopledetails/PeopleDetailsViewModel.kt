package com.claudiogalvaodev.moviemanager.ui.peopledetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.moviemanager.data.model.Employe
import com.claudiogalvaodev.moviemanager.data.model.Filter
import com.claudiogalvaodev.moviemanager.data.model.Movie
import com.claudiogalvaodev.moviemanager.usecases.GetMovieDetailsUseCase
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
import java.lang.Exception

class PeopleDetailsViewModel(
    val personId: Int,
    val leastOneMovieId: Int,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMoviesByCriteriousUseCase: GetMoviesByCriteriousUseCase,
    private val getPersonDetailsUseCase: GetPersonDetailsUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    private val _personDetails = MutableStateFlow<Employe?>(null)
    val personDetails = _personDetails.asStateFlow()

    private val _movies = MutableStateFlow<List<Movie>>(mutableListOf())
    val movies = _movies.asStateFlow()

    private var isFinishMovies: Boolean = false
    var isMoviesLoading: Boolean = false
    var isFirstLoading: Boolean = false
    var getSecondPage: Boolean = false

    private fun getFilter(): List<Filter> {
        val filters: MutableList<Filter> = mutableListOf()
        filters.add(Filter(type = FilterType.SORT_BY, name = "", currentValue = OrderByConstants.POPULARITY_DESC))
        filters.add(Filter(type = FilterType.PEOPLE, name = "", currentValue = personId.toString()))
        return filters
    }

    fun getPersonDetails() = viewModelScope.launch {
        withContext(dispatcher) {
            val personDetailsResult = getPersonDetailsUseCase.invoke(personId)

            if(personDetailsResult.isSuccess) {
                personDetailsResult.getOrNull()?.let { person ->
                    _personDetails.emit(person)
                }
            }
        }
    }

    fun getMovies() = viewModelScope.launch {
        withContext(dispatcher) {
            isMoviesLoading = true
            val moviesList = mutableListOf<Movie>()
            val moviesResult = if (!isFinishMovies) {
                getMoviesByCriteriousUseCase.invoke(getFilter(), isFirstLoading)
            } else Result.failure(Exception("There is no more movies to get"))

            if(isFirstLoading) {
                val movieDetailsResult = getMovieDetailsUseCase.invoke(leastOneMovieId)
                if(movieDetailsResult.isSuccess) {
                    movieDetailsResult.getOrNull()?.let { movieDetailsUI ->
                        moviesList.addAll(listOf(movieDetailsUI.movie))
                    }
                }
            }

            if(getSecondPage && _movies.value.isNotEmpty()) getSecondPage = false

            if(moviesResult.isSuccess) {
                moviesResult.getOrNull()?.let { movies ->
                    if(movies.size < MAX_ITEMS_ON_REQUEST) isFinishMovies = true
                    moviesList.addAll(_movies.value)
                    moviesList.addAll(movies.filter { movie -> movie.id != leastOneMovieId })
                    _movies.emit(moviesList)
                }
            }
            isMoviesLoading = false
            isFirstLoading = false
        }
    }

    companion object {
        const val MAX_ITEMS_ON_REQUEST = 20
    }

}