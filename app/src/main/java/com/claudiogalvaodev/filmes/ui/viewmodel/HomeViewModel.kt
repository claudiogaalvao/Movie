package com.claudiogalvaodev.filmes.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.claudiogalvaodev.filmes.data.bd.entity.MovieEntity
import com.claudiogalvaodev.filmes.repository.MoviesRepository
import com.claudiogalvaodev.filmes.repository.Result

class HomeViewModel(
    private val repository: MoviesRepository
): ViewModel() {

    fun getPopularMovies(): LiveData<Result<List<MovieEntity>?>> =
        repository.getPopularMovies()
}