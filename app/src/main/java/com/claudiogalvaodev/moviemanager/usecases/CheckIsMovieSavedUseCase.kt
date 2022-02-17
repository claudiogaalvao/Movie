package com.claudiogalvaodev.moviemanager.usecases

import com.claudiogalvaodev.moviemanager.data.repository.MoviesRepository

class CheckIsMovieSavedUseCase(
    private val repository: MoviesRepository
) {

    suspend fun invoke(movieId: Int) = repository.isMovieSaved(movieId = movieId)

}