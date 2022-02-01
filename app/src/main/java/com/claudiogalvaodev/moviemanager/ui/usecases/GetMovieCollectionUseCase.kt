package com.claudiogalvaodev.moviemanager.ui.usecases

import com.claudiogalvaodev.moviemanager.data.model.Collection
import com.claudiogalvaodev.moviemanager.data.repository.MoviesRepository

class GetMovieCollectionUseCase(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(movieId: Int): Result<Collection?> {
        return repository.getCollection(movieId)
    }

}