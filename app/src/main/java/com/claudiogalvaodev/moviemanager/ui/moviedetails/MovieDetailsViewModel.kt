package com.claudiogalvaodev.moviemanager.ui.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.moviemanager.data.bd.entity.UserListEntity
import com.claudiogalvaodev.moviemanager.data.model.*
import com.claudiogalvaodev.moviemanager.ui.model.BottomSheetOfListsUI
import com.claudiogalvaodev.moviemanager.usecases.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    val movieId: Int,
    val androidId: String,
    private val allMovieDetailsUseCase: AllMovieDetailsUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
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

    private val _userLists = MutableStateFlow<List<UserListEntity>>(emptyList())
    val myLists = _userLists.asStateFlow()

    private val _videos = MutableStateFlow<List<Video>>(emptyList())
    val videos = _videos.asStateFlow()

    init {
        getMovieDetails()
        getMovieCredits()
        getProviders()
        getAllUserLists()
        getVideos()
    }

    private fun getMovieDetails() = viewModelScope.launch(dispatcher) {
        val movieDetailsResult = allMovieDetailsUseCase.getMovieDetailsUseCase.invoke(movieId)
        if(movieDetailsResult.isSuccess) {
            val movieDetails = movieDetailsResult.getOrNull()
            movieDetails?.let { movieDetailsUI ->
                _movie.emit(movieDetailsUI.movie)
                _companies.emit(movieDetailsUI.companies)
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
        allMovieDetailsUseCase.getMovieCreditsUseCase.invoke(movieId)
        _stars.emit(allMovieDetailsUseCase.getMovieCreditsUseCase.stars.value)
        _directors.emit(allMovieDetailsUseCase.getMovieCreditsUseCase.directors.value)
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

    private fun getAllUserLists() = viewModelScope.launch(dispatcher) {
        allMovieDetailsUseCase.getAllUserListsUseCase.invoke().collectLatest { myLists ->
            _userLists.emit(myLists)
        }
    }

    fun saveMovieOnUserList(
        listSelected: BottomSheetOfListsUI
    ) = viewModelScope.launch(dispatcher) {
        _movie.value?.let {
            allMovieDetailsUseCase.saveMovieOnMyListUseCase.invoke(
                listSelected,
                it
            )
        }
    }

    fun removeMovieFromUserList(
        listSelected: BottomSheetOfListsUI
    ) = viewModelScope.launch(dispatcher) {
        _movie.value?.let {
            allMovieDetailsUseCase.removeMovieFromMyListUseCase
                .invoke(movieId = it.id, myListId = listSelected.id.toInt())
        }
    }

    fun createNewUserList(newListEntity: UserListEntity): Flow<Int> {
        val myListId = MutableStateFlow(0)
        viewModelScope.launch(dispatcher) {
            val listId = allMovieDetailsUseCase.createNewListOnMyListsUseCase.invoke(newListEntity)
            myListId.emit(listId.toInt())
        }
        return myListId
    }

}