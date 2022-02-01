package com.claudiogalvaodev.moviemanager.ui.peopledetails

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.moviemanager.R
import com.claudiogalvaodev.moviemanager.model.Filter
import com.claudiogalvaodev.moviemanager.model.Movie
import com.claudiogalvaodev.moviemanager.ui.usecases.GetMoviesByCriteriousUseCase
import com.claudiogalvaodev.moviemanager.utils.OrderByConstants
import com.claudiogalvaodev.moviemanager.utils.enum.FilterType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PeopleDetailsViewModel(
    private val getMoviesByCriteriousUseCase: GetMoviesByCriteriousUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    private val _movies = MutableStateFlow<List<Movie>>(mutableListOf())
    val movies = _movies.asStateFlow()

    // Fazer request para /person/1136406
    // Fazer request para /discover/movie?sort_by=popularity.desc&with_people=1136406

    // listar movies do discover
    var isLoading: Boolean = false
    var isUpdate: Boolean = false

    private var theresNoMoreMovies = false

    private fun getFilter(personId: String): List<Filter> {
        val filters: MutableList<Filter> = mutableListOf()
        filters.add(Filter(type = FilterType.SORT_BY, name = "", currentValue = OrderByConstants.POPULARITY_DESC))
        filters.add(Filter(type = FilterType.PEOPLE, name = "", currentValue = personId))
        return filters
    }

    fun getMovies(personId: String) = viewModelScope.launch {
        withContext(dispatcher) {
            isLoading = true
            val moviesResult = if(!theresNoMoreMovies) {
                getMoviesByCriteriousUseCase.invoke(getFilter(personId), isUpdate)
            } else {
                Result.failure(Exception())
            }

            if(moviesResult.isSuccess) {
                moviesResult.getOrNull()?.let { movies ->
                    if(movies.isEmpty()) {
                        theresNoMoreMovies = true
                        return@withContext
                    }
                    if(isUpdate) {
                        _movies.emit(movies)
                        isLoading = false
                        isUpdate = false
                        return@withContext
                    }
                    val moviesList = mutableListOf<Movie>()
                    moviesList.addAll(_movies.value)
                    moviesList.addAll(movies)
                    _movies.emit(moviesList)
                }
            }
            isLoading = false
            isUpdate = false
        }
    }

}