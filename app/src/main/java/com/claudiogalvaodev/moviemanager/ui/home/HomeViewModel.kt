package com.claudiogalvaodev.moviemanager.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.moviemanager.data.model.Event
import com.claudiogalvaodev.moviemanager.data.model.Movie
import com.claudiogalvaodev.moviemanager.usecases.GetTrendingWeekMoviesUseCase
import com.claudiogalvaodev.moviemanager.usecases.GetUpComingAndPlayingNowMoviesUseCase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*

class HomeViewModel(
    private val getTrendingWeekMoviesUseCase: GetTrendingWeekMoviesUseCase,
    private val getUpComingAndPlayingNowMoviesUseCase: GetUpComingAndPlayingNowMoviesUseCase,
    private val firestoreDB: FirebaseFirestore,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    private val _trendingMovies = MutableStateFlow<List<Movie>>(emptyList())
    val trendingMovies = _trendingMovies.asStateFlow()

    private val _upComingMovies = MutableStateFlow<List<Movie>>(emptyList())
    val upComingMovies = _upComingMovies.asStateFlow()

    private val _playingNowMovies = MutableStateFlow<List<Movie>>(emptyList())
    val playingNowMovies = _playingNowMovies.asStateFlow()

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events = _events.asStateFlow()

    init {
        getEvents()
        getTrendingMovies()
        getUpComingAndPlayingNow()
    }

    private fun getTrendingMovies() = viewModelScope.launch(dispatcher) {
        val moviesList = getTrendingWeekMoviesUseCase.invoke()
        if(moviesList.isSuccess) {
            _trendingMovies.emit(moviesList.getOrDefault(emptyList()))
        }
    }

    private fun getUpComingAndPlayingNow() = viewModelScope.launch(dispatcher) {
        getUpComingAndPlayingNowMoviesUseCase.invoke()

        _upComingMovies.emit(getUpComingAndPlayingNowMoviesUseCase.upComingMovies.value)
        _playingNowMovies.emit(getUpComingAndPlayingNowMoviesUseCase.playingNowMovies.value)
    }

    private fun getEvents() {
        try {
            firestoreDB.collection("events").get().addOnSuccessListener { snapshot ->
                if(snapshot != null) {
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
            }
        } catch (e: Exception) {
            Log.i("firestore error", "Something went wrong when try to get events from firestore")
        }
    }

}