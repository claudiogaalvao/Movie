package com.claudiogalvaodev.filmes.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.filmes.data.bd.entity.MovieEntity
import com.claudiogalvaodev.filmes.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val repository: MoviesRepository
): ViewModel() {

    private val _popularMovies = MutableLiveData<List<MovieEntity>>()
    val popularMovies: LiveData<List<MovieEntity>>
        get() = _popularMovies

    fun getPopularMovies() = viewModelScope.launch {
        // IO Possui threads reservadas
        // Default Compartilha uma mesma thread entre quem chamar esse tipo de dispatcher
        // Default mais recomendado para manipulações mais pesadas/longas
        withContext(Dispatchers.IO) {
            val moviesList = repository.getPopularMovies()
            if(moviesList.isSuccess) {
                _popularMovies.postValue(moviesList.getOrDefault(emptyList()))
            }
        }
    }

}