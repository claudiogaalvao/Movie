package com.claudiogalvaodev.moviemanager.usecases.customlists

import com.claudiogalvaodev.moviemanager.data.repository.ICustomListsRepository
import com.claudiogalvaodev.moviemanager.ui.model.CustomListModel

class RemoveMovieFromCustomListUseCase(
    private val repository: ICustomListsRepository
) {

    suspend operator fun invoke(movieId: Int, selectedList: CustomListModel?): Result<Unit> {
        selectedList?.let { customListModel ->
            val shouldUpdateCustomListPosterPath = customListModel.movies.first().id == movieId
            var newPosterPath: String? = null
            if (shouldUpdateCustomListPosterPath) {
                val secondMovie = customListModel.movies.getOrNull(1)
                newPosterPath = secondMovie?.posterPath ?: ""
            }
            repository.removeMoveFromCustomList(movieId, selectedList.id, newPosterPath)
            return Result.success(Unit)
        }
        return Result.failure(Exception("List does not exists to remove movie from that"))
    }

}