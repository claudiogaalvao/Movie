package com.claudiogalvaodev.moviemanager.usecases.movies

import com.claudiogalvaodev.moviemanager.data.repository.IMoviesRepository
import com.claudiogalvaodev.moviemanager.ui.model.CollectionModel
import com.claudiogalvaodev.moviemanager.ui.model.sortCollectionByAscendingDate

class GetMovieCollectionUseCase(
    private val repository: IMoviesRepository
) {

    suspend operator fun invoke(movieId: Int): Result<CollectionModel?> {
        val collectionResult = repository.getCollection(movieId)
        if(collectionResult.isSuccess) {
            val collection = collectionResult.getOrNull()
            if(collection != null) {
                return Result.success(collection.sortCollectionByAscendingDate())
            }
        }
        return collectionResult
    }

}