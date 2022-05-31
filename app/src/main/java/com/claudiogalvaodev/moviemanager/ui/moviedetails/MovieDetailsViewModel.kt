package com.claudiogalvaodev.moviemanager.ui.moviedetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.moviemanager.data.bd.entity.MovieSaved
import com.claudiogalvaodev.moviemanager.data.bd.entity.MyList
import com.claudiogalvaodev.moviemanager.data.model.*
import com.claudiogalvaodev.moviemanager.ui.model.BottomSheetOfListsUI
import com.claudiogalvaodev.moviemanager.ui.model.SaveOn
import com.claudiogalvaodev.moviemanager.usecases.*
import com.claudiogalvaodev.moviemanager.utils.Constants
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

class MovieDetailsViewModel(
    val movieId: Int,
    val androidId: String,
    private val firestoreDB: FirebaseFirestore,
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

    private val _videos = MutableStateFlow<List<Video>>(emptyList())
    val videos = _videos.asStateFlow()

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events = _events.asStateFlow()

    init {
        getAllMoviesSaved()
        getMovieDetails()
        getMovieCredits()
        getProviders()
        getAllMyLists()
        checkIsMovieSaved()
        getVideos()
        getAllEvents()
    }

    private fun getAllMoviesSaved() = viewModelScope.launch {
        allMovieDetailsUseCase.getAllMoviesSavedUseCase.invoke().collectLatest { moviesSaved ->
            _moviesSaved.emit(moviesSaved)
        }
    }

    private fun checkIsMovieSaved() = viewModelScope.launch(dispatcher) {
        val result = allMovieDetailsUseCase.checkIsMovieSavedUseCase.invoke(movieId)
        _isMovieSaved.emit(result)
    }

    fun saveMovieOnList(listSelected: BottomSheetOfListsUI, callback: (isSuccess: Boolean) -> Unit) {
        when (listSelected.saveOn) {
            SaveOn.USER_LIST -> saveOnUserList(listSelected, callback)
            SaveOn.SPECIAL_LIST -> saveMovieToSpecialList(listSelected, callback)
        }
    }

    private fun saveOnUserList(
        listSelected: BottomSheetOfListsUI,
        callback: (isSuccess: Boolean) -> Unit
    ) = viewModelScope.launch {
        _movie.value?.let {
            val result = allMovieDetailsUseCase.saveMovieToMyListUseCase.invoke(
                MovieSaved(
                    id = 0,
                    movieId = it.id,
                    moviePosterUrl = it.getPoster(),
                    myListId = listSelected.id.toInt()
                )
            )
            if (result.isSuccess) {
                checkIsMovieSaved()
                callback.invoke(true)
            }
        }
    }

    private fun saveMovieToSpecialList(
        listSelected: BottomSheetOfListsUI,
        callback: (isSuccess: Boolean) -> Unit
    ) {
        if (isAdmin()) {
            _movie.value?.let{

                val imageUrl = hashMapOf(
                    "enUS" to it.getPoster(),
                    "ptBR" to it.getPoster()
                )
                val title = hashMapOf(
                    "enUS" to it.original_title,
                    "ptBR" to it.title
                )

                val newItem = hashMapOf(
                    "eventRef" to listSelected.id,
                    "imageUrl" to imageUrl,
                    "itemId" to it.id,
                    "releaseDate" to it.release_date,
                    "title" to title,
                    "type" to "MOVIE"
                )

                firestoreDB.collection("itemsForEvents")
                    .document("movie-${it.original_title}")
                    .set(newItem)
                    .addOnSuccessListener { callback.invoke(true) }
                    .addOnFailureListener { callback.invoke(false) }
            }
        }
    }

    fun removeMovieFromList(
        listSelected: BottomSheetOfListsUI,
        callback: (isSuccess: Boolean) -> Unit
    ) {
        when (listSelected.saveOn) {
            SaveOn.USER_LIST -> removeMovieFromUserList(listSelected, callback)
            SaveOn.SPECIAL_LIST -> removeMovieFromSpecialList(listSelected, callback)
        }
    }

    private fun removeMovieFromUserList(
        listSelected: BottomSheetOfListsUI,
        callback: (isSuccess: Boolean) -> Unit
    ) = viewModelScope.launch {
        _movie.value?.let {
            val result =  allMovieDetailsUseCase.removeMovieFromMyListUseCase
                .invoke(movieId = it.id, myListId = listSelected.id.toInt())
            if (result.isSuccess) {
                checkIsMovieSaved()
                callback.invoke(true)
            }
        }
    }

    private fun removeMovieFromSpecialList(
        listSelected: BottomSheetOfListsUI,
        callback: (isSuccess: Boolean) -> Unit
    ) {
        if (isAdmin()) {
            firestoreDB.collection("itemsForEvents")
                .document(listSelected.id)
                .delete()
                .addOnSuccessListener { callback.invoke(true) }
                .addOnFailureListener { callback.invoke(false) }
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

    private fun getAllMyLists() = viewModelScope.launch {
        allMovieDetailsUseCase.getAllMyListsUseCase.invoke().collectLatest { myLists ->
            _myLists.emit(myLists)
        }
    }

    private fun getMovieDetails() = viewModelScope.launch {
        val movieDetailsResult = allMovieDetailsUseCase.getMovieDetailsUseCase.invoke(movieId)
        if(movieDetailsResult.isSuccess) {
            val movieDetails = movieDetailsResult.getOrNull()
            movieDetails?.let { movieDetailsUI ->
                _movie.emit(movieDetailsUI.movie)
                _companies.emit(movieDetailsUI.companies)
            }
        }
    }

    private fun getVideos() = viewModelScope.launch {
        val videosResult = allMovieDetailsUseCase.getVideosFromMovieUseCase.invoke(movieId)
        if (videosResult.isSuccess) {
            val videos = videosResult.getOrNull()
            videos?.let { _videos.emit(it) }
        }
    }

    private fun getProviders() = viewModelScope.launch {
        val streamProvidersResult = allMovieDetailsUseCase.getMovieProvidersUseCase.invoke(movieId)
        if(streamProvidersResult.isSuccess) {
            val stream = streamProvidersResult.getOrDefault(emptyList())
            if(stream != null) {
                _streamProviders.emit(stream)
            }
        }
    }

    private fun getMovieCredits() = viewModelScope.launch {
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

    fun isAdmin() = !Constants.ADMINS_DEVICE.contains(androidId)

    private fun getAllEvents() {
        try {
            firestoreDB.collection("events").get()
                .addOnSuccessListener { snapshot ->
                    val currentLanguage = Locale.getDefault().toLanguageTag().replace("-", "")

                    val allEvents: MutableList<Event> = mutableListOf()
                    for (document in snapshot.documents) {
                        val event = Event(
                            id = document.id,
                            title = document["title.${currentLanguage}"].toString(),
                            description = document["description.${currentLanguage}"].toString(),
                            imageUrl = document["imageUrl.${currentLanguage}"].toString(),
                            eventDate = document["eventDate"].toString(),
                            startAt = document["startAt"].toString(),
                            finishAt = document["finishAt"].toString(),
                        )
                        allEvents.add(event)
                    }
                    _events.value = allEvents
                }
        } catch (e: Exception) {
            Log.i("firestore error", "Something went wrong when try to get events from firestore")
        }
    }

}