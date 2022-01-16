package com.claudiogalvaodev.moviemanager.ui.usecases

import com.claudiogalvaodev.moviemanager.model.Collection
import com.claudiogalvaodev.moviemanager.repository.MoviesRepository

class GetMovieCollectionUseCase(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(movieId: Int): Result<Collection?> {
        val collectionResult = repository.getCollection(movieId)
        if(collectionResult.isSuccess) {
            return collectionResult
        }
        return collectionResult
    }

}