package com.claudiogalvaodev.moviemanager.ui.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.moviemanager.data.bd.entity.MovieSaved
import com.claudiogalvaodev.moviemanager.data.bd.entity.MyList
import com.claudiogalvaodev.moviemanager.data.model.Company
import com.claudiogalvaodev.moviemanager.data.model.Employe
import com.claudiogalvaodev.moviemanager.data.model.Movie
import com.claudiogalvaodev.moviemanager.data.model.Provider
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

    private val _myLists = MutableStateFlow<List<MyList>>(emptyList())
    val myLists = _myLists.asStateFlow()

    private val _moviesSaved = MutableStateFlow<List<MovieSaved>>(emptyList())
    val moviesSaved = _moviesSaved.asStateFlow()

    private val _isMovieSaved = MutableStateFlow(false)
    val isMovieSaved = _isMovieSaved.asStateFlow()

    init {
        viewModelScope.launch {
            allMovieDetailsUseCase.getAllMoviesSavedUseCase.invoke().collectLatest { moviesSaved ->
                _moviesSaved.emit(moviesSaved)
            }
        }
    }

    fun checkIsMovieSaved() = viewModelScope.launch(dispatcher) {
        val result = allMovieDetailsUseCase.checkIsMovieSavedUseCase.invoke(movieId)
        _isMovieSaved.emit(result)
    }

    fun saveMovieToMyList(myListId: Int): Flow<Boolean> {
        val successfullySaved = MutableStateFlow(false)
        viewModelScope.launch {
            val movie = _movie.value

            movie?.let {
                val result = allMovieDetailsUseCase.saveMovieToMyListUseCase.invoke(
                    MovieSaved(
                        id = 0,
                        movieId = it.id,
                        moviePosterUrl = it.getPoster(),
                        myListId = myListId
                    ))
                if(result.isSuccess) {
                    successfullySaved.emit(true)
                }
            }
        }
        return successfullySaved
    }

    fun removeMovieFromMyList(myListId: Int) {
        viewModelScope.launch {
            val movie = _movie.value

            movie?.let {
                allMovieDetailsUseCase.removeMovieFromMyListUseCase.invoke(movieId = it.id, myListId = myListId)
            }
        }
    }

    fun createNewList(newList: MyList): Flow<Int> {
        val myListId = MutableStateFlow(0)
        viewModelScope.launch {
            val listId = allMovieDetailsUseCase.createNewListOnMyListsUseCase.invoke(newList)
            myListId.emit(listId.toInt())
        }
        return myListId
    }

    fun getAllMyLists() = viewModelScope.launch {
        allMovieDetailsUseCase.getAllMyListsUseCase.invoke().collectLatest { myLists ->
            _myLists.emit(myLists)
        }
    }

    fun getMovieDetails() = viewModelScope.launch {
        val movieDetailsResult = allMovieDetailsUseCase.getMovieDetailsUseCase.invoke(movieId)
        if(movieDetailsResult.isSuccess) {
            val movieDetails = movieDetailsResult.getOrNull()
            movieDetails?.let { movieDetailsUI ->
                _movie.emit(movieDetailsUI.movie)
                _companies.emit(movieDetailsUI.companies)
            }
        }
    }

    fun getProviders() = viewModelScope.launch {
        val streamProvidersResult = allMovieDetailsUseCase.getMovieProvidersUseCase.invoke(movieId)
        if(streamProvidersResult.isSuccess) {
            val stream = streamProvidersResult.getOrDefault(emptyList())
            if(stream != null) {
                _streamProviders.emit(stream)
            }
        }
    }

    fun getMovieCredits() = viewModelScope.launch {
        allMovieDetailsUseCase.getMovieCreditsUseCase.invoke(movieId)
        _stars.emit(allMovieDetailsUseCase.getMovieCreditsUseCase.stars.value)
        _directors.emit(allMovieDetailsUseCase.getMovieCreditsUseCase.directors.value)
    }

    fun getMovieCollection(collectionId: Int) = viewModelScope.launch {
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

}