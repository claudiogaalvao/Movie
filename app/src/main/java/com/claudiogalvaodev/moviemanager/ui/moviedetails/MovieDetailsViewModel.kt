package com.claudiogalvaodev.moviemanager.ui.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.moviemanager.ui.model.*
import com.claudiogalvaodev.moviemanager.usecases.movies.AllMovieDetailsUseCase
import com.claudiogalvaodev.moviemanager.usecases.notification.HasMovieReleaseScheduledNotification
import com.claudiogalvaodev.moviemanager.usecases.notification.ScheduleMovieReleaseNotificationUseCase
import com.claudiogalvaodev.moviemanager.utils.format.FormatUtils.isFutureDate
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    val movieId: Int,
    val releaseDate: String,
    val androidId: String,
    private val allMovieDetailsUseCase: AllMovieDetailsUseCase,
    private val scheduleMovieReleaseNotificationUseCase: ScheduleMovieReleaseNotificationUseCase,
    private val hasMovieReleaseScheduledNotification: HasMovieReleaseScheduledNotification,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
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

    private val _isActiveRemindAt = MutableStateFlow(false)
    val isActiveRemindAt = _isActiveRemindAt.asStateFlow()

    init {
        getMovieDetails()
        getMovieCredits()
        getProviders()
        getAllCustomLists()
        getVideos()
        checkIfHasMovieReleaseScheduledNotification()
    }

    private fun getMovieDetails() = viewModelScope.launch(ioDispatcher) {
        val movieDetailsResult = allMovieDetailsUseCase.getMovieDetailsUseCase.invoke(movieId)
        if(movieDetailsResult.isSuccess) {
            val movieDetails = movieDetailsResult.getOrNull()
            movieDetails?.let {
                _movie.emit(it.copy(releaseDate = releaseDate))
                _companies.emit(it.productionCompanies)
            }
        }
    }

    private fun getVideos() = viewModelScope.launch(ioDispatcher) {
        val videosResult = allMovieDetailsUseCase.getVideosFromMovieUseCase.invoke(movieId)
        if (videosResult.isSuccess) {
            val videos = videosResult.getOrNull()
            videos?.let { _videos.emit(it) }
        }
    }

    private fun getProviders() = viewModelScope.launch(ioDispatcher) {
        val streamProvidersResult = allMovieDetailsUseCase.getMovieProvidersUseCase.invoke(movieId)
        if(streamProvidersResult.isSuccess) {
            val stream = streamProvidersResult.getOrDefault(emptyList())
            if(stream != null) {
                _streamProviders.emit(stream)
            }
        }
    }

    private fun getMovieCredits() = viewModelScope.launch(ioDispatcher) {
        val creditsModelResult = allMovieDetailsUseCase.getMovieCreditsUseCase.invoke(movieId)
        if (creditsModelResult.isSuccess) {
            val creditsModel = creditsModelResult.getOrNull()
            creditsModel?.let {
                _actors.emit(it.actors)
                _directors.emit(it.directors)
            }
        }
    }

    fun getMovieCollection(collectionId: Int) = viewModelScope.launch(ioDispatcher) {
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

    fun shouldShowActionRememberMe(): Boolean {
        return _movie.value?.releaseDate?.let { releaseDate ->
            isFutureDate(releaseDate)
        } ?: false
    }

    private fun getAllCustomLists() = viewModelScope.launch(ioDispatcher) {
        allMovieDetailsUseCase.getAllCustomListsUseCase.invoke().collectLatest { customListsResult ->
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
        allMovieDetailsUseCase.saveMovieOnCustomListUseCase.invoke(
            listId = listId,
            movieId = it.id,
            posterPath = it.posterPath
        ).isSuccess
    } ?: false

    suspend fun removeMovieFromCustomList(
        listSelected: BottomSheetOfListsUI
    ): Boolean = _movie.value?.let {
        val selectedList = customLists.value.find { it.id == listSelected.id }
        allMovieDetailsUseCase.removeMovieFromCustomListUseCase(movieId = it.id, selectedList = selectedList).isSuccess
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

    fun scheduleNotification(
        notificationTitle: String,
        notificationDescription: String
    ) = viewModelScope.launch(ioDispatcher) {
        movie.value?.let {
            scheduleMovieReleaseNotificationUseCase(it, notificationTitle, notificationDescription)
        }
    }

    private fun checkIfHasMovieReleaseScheduledNotification() = viewModelScope.launch(ioDispatcher) {
        movie.value?.let {
            val result = hasMovieReleaseScheduledNotification(it.id)
            _isActiveRemindAt.emit(result)
        }
    }

}