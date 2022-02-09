package com.claudiogalvaodev.moviemanager.usecases

import com.claudiogalvaodev.moviemanager.data.repository.MoviesRepository

class GetAllMoviesSavedUseCase(
    private val repository: MoviesRepository
) {

    fun invoke() = repository.getAllMoviesSaved()

}