package com.claudiogalvaodev.moviemanager.usecases.customlists

import com.claudiogalvaodev.moviemanager.data.repository.ICustomListsRepository

class SaveMovieOnCustomListUseCase(
    private val repository: ICustomListsRepository
) {

    suspend operator fun invoke(listId: Int, movieId: Int, posterPath: String): Result<Unit> {
        repository.saveMovieOnCustomList(listId, movieId, posterPath)
        return Result.success(Unit)
    }

}