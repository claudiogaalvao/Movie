package com.claudiogalvaodev.moviemanager.ui.usecases

import com.claudiogalvaodev.moviemanager.data.bd.entity.MovieSaved
import com.claudiogalvaodev.moviemanager.data.repository.MoviesRepository

class SaveMovieToMyListUseCase(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(
        movieSaved: MovieSaved
    ): Result<Unit> = repository.saveMovieToMyList(movieSaved)

}