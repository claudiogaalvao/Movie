package com.claudiogalvaodev.moviemanager.ui.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.moviemanager.ui.model.*
import com.claudiogalvaodev.moviemanager.usecases.movies.AllMovieDetailsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    val movieId: Int,
    val androidId: String,
    private val allMovieDetailsUseCase: AllMovieDetailsUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {
    private val _movie = MutableStateFlow<MovieModel?>(null)
    val movie = _movie.asStateFlow()

    private val _streamProviders = MutableStateFlow<List<ProviderModel>?>(emptyList())
    val streamProviders = _streamProviders.asStateFlow()

    private val _directors = MutableStateFlow<List<PersonModel>?>(emptyList())
    val directors = _directors.asStateFlow()

    private val _actors = MutableStateFlow<List<PersonModel>?>(emptyList())
    val stars = _actors.asStateFlow()

    private val _companies = MutableStateFlow<List<ProductionCompanyModel>?>(emptyList())
    val companies = _companies.asStateFlow()

    private val _collection = MutableStateFlow<List<MovieModel>?>(emptyList())
    val collection = _collection.asStateFlow()

    private val _customLists = MutableStateFlow<List<CustomListModel>>(emptyList())
    val customLists = _customLists.asStateFlow()

    private val _isMovieSaved = MutableStateFlow(false)
    val isMovieSaved = _isMovieSaved.asStateFlow()

    private val _videos = MutableStateFlow<List<VideoModel>>(emptyList())
    val videos = _videos.asStateFlow()

    init {
        getMovieDetails()
        getMovieCredits()
        getProviders()
        getAllCustomLists()
        getVideos()
    }

    private fun getMovieDetails() = viewModelScope.launch(dispatcher) {
        val movieDetailsResult = allMovieDetailsUseCase.getMovieDetailsUseCase.invoke(movieId)
        if(movieDetailsResult.isSuccess) {
            val movieDetails = movieDetailsResult.getOrNull()
            movieDetails?.let {
                _movie.emit(it)
                _companies.emit(it.productionCompanies)
            }
        }
    }

    private fun getVideos() = viewModelScope.launch(dispatcher) {
        val videosResult = allMovieDetailsUseCase.getVideosFromMovieUseCase.invoke(movieId)
        if (videosResult.isSuccess) {
            val videos = videosResult.getOrNull()
            videos?.let { _videos.emit(it) }
        }
    }

    private fun getProviders() = viewModelScope.launch(dispatcher) {
        val streamProvidersResult = allMovieDetailsUseCase.getMovieProvidersUseCase.invoke(movieId)
        if(streamProvidersResult.isSuccess) {
            val stream = streamProvidersResult.getOrDefault(emptyList())
            if(stream != null) {
                _streamProviders.emit(stream)
            }
        }
    }

    private fun getMovieCredits() = viewModelScope.launch(dispatcher) {
        val creditsModelResult = allMovieDetailsUseCase.getMovieCreditsUseCase.invoke(movieId)
        if (creditsModelResult.isSuccess) {
            val creditsModel = creditsModelResult.getOrNull()
            creditsModel?.let {
                _actors.emit(it.actors)
                _directors.emit(it.directors)
            }
        }
    }

    fun getMovieCollection(collectionId: Int) = viewModelScope.launch(dispatcher) {
        val movieCollectionResult = allMovieDetailsUseCase.getMovieCollectionUseCase.invoke(collectionId)
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

    private fun getAllCustomLists() = viewModelScope.launch(dispatcher) {
        val customListsResult = allMovieDetailsUseCase.getAllCustomListsUseCase.invoke()
        if (customListsResult.isSuccess) {
            val customLists = customListsResult.getOrNull()
            customLists?.let {
                _customLists.emit(it)
                _isMovieSaved.emit(hasMovieSaved(it))
            }
        }
    }

    private fun hasMovieSaved(customList: List<CustomListModel>): Boolean {
        val hasMovie = customList.find { customListModel ->
            customListModel.movies.find { movieModel ->
                movieModel.id == movieId
            } != null
        } != null
        return hasMovie
    }

    suspend fun saveMovieOnCustomList(
        listId: Int
    ): Boolean = _movie.value?.let {
        val result = allMovieDetailsUseCase.saveMovieOnCustomListUseCase.invoke(
            listId = listId,
            movieId = it.id,
            posterPath = it.posterPath
        )
        result.isSuccess
    } ?: false

    suspend fun removeMovieFromCustomList(
        listSelected: BottomSheetOfListsUI
    ): Boolean = _movie.value?.let {
        val result = allMovieDetailsUseCase.removeMovieFromCustomListUseCase
            .invoke(movieId = it.id, listId = listSelected.id)
        result.isSuccess
    } ?: false

    suspend fun createNewCustomListThenSaveMovie(listName: String): Boolean {
        val listIdResult = allMovieDetailsUseCase.createNewCustomListUseCase.invoke(listName)
        if (listIdResult.isSuccess) {
            val listId = listIdResult.getOrNull()
            listId?.let {
                return saveMovieOnCustomList(listId = it)
            }
        }
        return false
    }

}