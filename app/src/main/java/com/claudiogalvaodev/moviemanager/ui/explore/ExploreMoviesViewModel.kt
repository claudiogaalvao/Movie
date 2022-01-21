package com.claudiogalvaodev.moviemanager.ui.explore

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.moviemanager.R
import com.claudiogalvaodev.moviemanager.model.Filter
import com.claudiogalvaodev.moviemanager.model.Movie
import com.claudiogalvaodev.moviemanager.ui.usecases.GetMoviesByCriteriousUseCase
import com.claudiogalvaodev.moviemanager.utils.SortByConstants
import com.claudiogalvaodev.moviemanager.utils.enum.FilterType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExploreMoviesViewModel(
    private val context: Context,
    private val getMoviesByCriteriousUseCase: GetMoviesByCriteriousUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    private val _filters = MutableStateFlow<List<Filter>>(emptyList())
    val filters = _filters.asStateFlow()

    private val _movies = MutableStateFlow<List<Movie>>(mutableListOf())
    val movies = _movies.asStateFlow()

    var isLoading: Boolean = false

    fun initFilters() = viewModelScope.launch {
        withContext(dispatcher) {
            val filters = listOf(
                Filter(type = FilterType.SORT_BY,
                    name = context.resources.getString(R.string.filter_type_orderby),
                    currentValue = SortByConstants.POPULARITY_DESC),
                Filter(type = FilterType.GENDERS,
                    name = context.resources.getString(R.string.filter_type_genders),
                    currentValue = ""),
                Filter(type = FilterType.ACTORS,
                    name = context.resources.getString(R.string.filter_type_actors),
                    currentValue = ""),
                Filter(type = FilterType.DIRECTORS,
                    name = context.resources.getString(R.string.filter_type_directors),
                    currentValue = ""),
                Filter(type = FilterType.COMPANIES,
                    name = context.resources.getString(R.string.filter_type_companies),
                    currentValue = ""),
                Filter(type = FilterType.YEARS,
                    name = context.resources.getString(R.string.filter_type_years),
                    currentValue = "")
            )
            _filters.emit(filters)
        }
    }

    fun getMoviesByCriterious() = viewModelScope.launch {
        withContext(dispatcher) {
            isLoading = true
            val moviesResult = getMoviesByCriteriousUseCase.invoke()

            if(moviesResult.isSuccess) {
                moviesResult.getOrNull()?.let { movies ->
                    val moviesList = mutableListOf<Movie>()
                    moviesList.addAll(_movies.value)
                    moviesList.addAll(movies)
                    _movies.emit(moviesList)
                }
            }
            isLoading = false
        }
    }

}