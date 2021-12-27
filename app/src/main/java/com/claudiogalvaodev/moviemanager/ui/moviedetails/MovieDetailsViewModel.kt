package com.claudiogalvaodev.moviemanager.ui.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.moviemanager.model.*
import com.claudiogalvaodev.moviemanager.repository.MoviesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val repository: MoviesRepository
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
        val movieDetailsResult = repository.getDetails(movieId)
        if(movieDetailsResult.isSuccess) {
            val movieDetails = movieDetailsResult.getOrDefault(null)
            if(movieDetails != null) {
                _movie.emit(movieDetails)
                _companies.emit(filterCompanies(movieDetails.production_companies))
            }
        }
    }

    fun getProviders(movieId: Int) = viewModelScope.launch {
        val streamProvidersResult = repository.getProviders(movieId)
        if(streamProvidersResult.isSuccess) {
            val stream = streamProvidersResult.getOrDefault(emptyList())
            if(stream != null) {
                _streamProviders.emit(stream)
            }
        }
    }

    fun getMovieCredits(movieId: Int) = viewModelScope.launch {
        val movieCreditsResult = repository.getCredits(movieId)
        if(movieCreditsResult.isSuccess) {
            val movieCredits = movieCreditsResult.getOrDefault(null)
            if(movieCredits != null) {
                _stars.emit(filterStars(movieCredits))
                _directors.emit(filterDirectors(movieCredits))
            }
        }
    }

    fun getMovieCollection(collectionId: Int) = viewModelScope.launch {
        val movieCollectionResult = repository.getCollection(collectionId)
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

    private fun filterStars(credits: Credits): List<Employe> {
        return credits.cast.filter { employe -> employe.known_for_department == "Acting" && employe.profile_path != null }
    }

    private fun filterDirectors(credits: Credits): List<Employe> {
        val castDirectors = credits.cast.filter { employe -> employe.known_for_department == "Directing" && employe.profile_path != null }
        val crewDirectors = credits.crew.filter { employe -> employe.known_for_department == "Directing" && employe.profile_path != null }
        val directors: MutableList<Employe> = arrayListOf()
        directors.addAll(castDirectors)
        directors.addAll(crewDirectors)
        return directors.distinct().toList()
    }

    private fun filterCompanies(companies: List<Company>): List<Company> {
        return companies.filter { company -> company.logo_path != null }
    }

}