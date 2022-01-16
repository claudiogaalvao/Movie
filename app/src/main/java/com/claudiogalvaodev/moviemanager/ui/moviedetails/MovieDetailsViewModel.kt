package com.claudiogalvaodev.moviemanager.ui.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.moviemanager.model.Company
import com.claudiogalvaodev.moviemanager.model.Employe
import com.claudiogalvaodev.moviemanager.model.Movie
import com.claudiogalvaodev.moviemanager.model.Provider
import com.claudiogalvaodev.moviemanager.ui.usecases.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val getMovieDetailsUseCases: GetMovieDetailsUseCases
): ViewModel() {

    private val _movie = MutableStateFlow<Movie?>(null)
    val movie = _movie.asStateFlow()

    private val _streamProviders = MutableStateFlow<List<Provider>?>(emptyList())
    val streamProviders = _streamProviders.asStateFlow()

    private val _directors = MutableStateFlow<List<Employe>?>(emptyList())
    val directors = _directors.asStateFlow()

    private val _stars = MutableStateFlow<List<Employe>?>(emptyList())
    val stars = _stars.asStateFlow()

    private val _companies = MutableStateFlow<List<Company>?>(emptyList())
    val companies = _companies.asStateFlow()

    private val _collection = MutableStateFlow<List<Movie>?>(emptyList())
    val collection = _collection.asStateFlow()

    fun getMovieDetails(movieId: Int) = viewModelScope.launch {
        getMovieDetailsUseCases.getMovieDetailsUseCase.invoke(movieId)
        _movie.emit(getMovieDetailsUseCases.getMovieDetailsUseCase.movie.value)
        _companies.emit(getMovieDetailsUseCases.getMovieDetailsUseCase.companies.value)
    }

    fun getProviders(movieId: Int) = viewModelScope.launch {
        val streamProvidersResult = getMovieDetailsUseCases.getMovieProvidersUseCase.invoke(movieId)
        if(streamProvidersResult.isSuccess) {
            val stream = streamProvidersResult.getOrDefault(emptyList())
            if(stream != null) {
                _streamProviders.emit(stream)
            }
        }
    }

    fun getMovieCredits(movieId: Int) = viewModelScope.launch {
        getMovieDetailsUseCases.getMovieCreditsUseCase.invoke(movieId)
        _stars.emit(getMovieDetailsUseCases.getMovieCreditsUseCase.stars.value)
        _directors.emit(getMovieDetailsUseCases.getMovieCreditsUseCase.directors.value)
    }

    fun getMovieCollection(collectionId: Int) = viewModelScope.launch {
        val movieCollectionResult = getMovieDetailsUseCases.getMovieCollectionUseCase.invoke(collectionId)
        if(movieCollectionResult.isSuccess) {
            val movieCompleteCollection = movieCollectionResult.getOrDefault(null)
            if(movieCompleteCollection?.parts?.isNotEmpty() == true) {
                _collection.emit(movieCompleteCollection.parts)
            }
        }
    }

    fun getStarsName(): String {
        var starsConcat = ""
        stars.value?.map { employe ->
            starsConcat += if(stars.value?.last()?.name == employe.name) {
                employe.name
            } else "${employe.name}, "
        }
        return starsConcat
    }

    fun getDirectorsName(): String {
        var directorsConcat = ""
        directors.value?.map { employe ->
            directorsConcat += if(directors.value?.last()?.name == employe.name) {
                employe.name
            } else "${employe.name}, "
        }
        return directorsConcat
    }

    fun getCompaniesName(): String {
        var companiesConcat = ""
        companies.value?.map { company ->
            companiesConcat += if(companies.value?.last()?.name == company.name) {
                company.name
            } else "${company.name}, "
        }
        return companiesConcat
    }

}