package com.claudiogalvaodev.moviemanager.ui.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.moviemanager.R
import com.claudiogalvaodev.moviemanager.ui.model.FilterModel
import com.claudiogalvaodev.moviemanager.ui.model.MovieModel
import com.claudiogalvaodev.moviemanager.usecases.movies.GetMoviesByCriterionUseCase
import com.claudiogalvaodev.moviemanager.utils.OrderByConstants
import com.claudiogalvaodev.moviemanager.utils.enums.FilterType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExploreMoviesViewModel(
    private val getMoviesByCriterionUseCase: GetMoviesByCriterionUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    private val _filters = MutableStateFlow<List<FilterModel>>(emptyList())
    val filters = _filters.asStateFlow()

    private val _movies = MutableStateFlow<List<MovieModel>>(mutableListOf())
    val movies = _movies.asStateFlow()

    var isLoading: Boolean = false
    private var isFirstLoading: Boolean = false
    var getSecondPage: Boolean = false

    init {
        viewModelScope.launch(dispatcher) {
            val filters = listOf(
                FilterModel(
                    type = FilterType.SORT_BY,
                    nameRes = R.string.filter_type_orderby,
                    currentValue = OrderByConstants.POPULARITY_DESC
                ),
                FilterModel(
                    type = FilterType.PROVIDERS,
                    nameRes = R.string.filter_providers_name,
                    currentValue = ""
                ),
                FilterModel(
                    type = FilterType.GENRES,
                    nameRes = R.string.filter_type_genres,
                    currentValue = ""
                ),
                FilterModel(
                    type = FilterType.PEOPLE,
                    nameRes = R.string.filter_type_people,
                    currentValue = ""
                ),
                FilterModel(
                    type = FilterType.YEARS,
                    nameRes = R.string.filter_type_years,
                    currentValue = ""
                )
            )
            _filters.emit(filters)
        }
    }

    fun getMoviesByCriterious() = viewModelScope.launch {
        withContext(dispatcher) {
            isLoading = true
            val moviesResult = getMoviesByCriterionUseCase.invoke(_filters.value, isFirstLoading)

            if(getSecondPage && _movies.value.isNotEmpty()) getSecondPage = false

            if(moviesResult.isSuccess) {
                moviesResult.getOrNull()?.let { movies ->

                    if(isFirstLoading) {
                        _movies.emit(movies)
                        isLoading = false
                        isFirstLoading = false
                        return@withContext
                    }
                    val moviesList = mutableListOf<MovieModel>()
                    moviesList.addAll(_movies.value)
                    moviesList.addAll(movies)
                    _movies.emit(moviesList)
                }
            }
            isLoading = false
            isFirstLoading = false
        }
    }

    fun updateFilter(filterChanged: FilterModel) = viewModelScope.launch {
        withContext(dispatcher) {
            val newFilters = _filters.value.toMutableList()
            newFilters.map { filter ->
                if (filter.type == filterChanged.type) {
                    if(filter.currentValue == filterChanged.currentValue) return@withContext
                    filter.currentValue = filterChanged.currentValue
                }
            }
            isFirstLoading = true
            _filters.emit(newFilters)
            getMoviesByCriterious()
        }
    }

}