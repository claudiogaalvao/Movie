package com.claudiogalvaodev.moviemanager.usecases

import com.claudiogalvaodev.moviemanager.data.repository.MoviesRepository
import kotlinx.coroutines.flow.collectLatest

class RemoveMovieFromMyListUseCase(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(
        movieId: Int, myListId: Int
    ): Result<Unit> {
        repository.removeMoveFromMyList(movieId, myListId)
        return Result.success(Unit)
    }

}