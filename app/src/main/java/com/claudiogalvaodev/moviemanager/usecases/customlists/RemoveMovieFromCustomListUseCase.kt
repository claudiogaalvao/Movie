package com.claudiogalvaodev.moviemanager.usecases.customlists

import com.claudiogalvaodev.moviemanager.data.repository.ICustomListsRepository

class RemoveMovieFromCustomListUseCase(
    private val repository: ICustomListsRepository
) {

    suspend operator fun invoke(movieId: Int, listId: Int): Result<Unit> {
        repository.removeMoveFromCustomList(movieId, listId)
        return Result.success(Unit)
    }

}