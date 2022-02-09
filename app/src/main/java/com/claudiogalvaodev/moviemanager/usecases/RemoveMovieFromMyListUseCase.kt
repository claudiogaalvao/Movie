package com.claudiogalvaodev.moviemanager.usecases

import com.claudiogalvaodev.moviemanager.data.bd.entity.MovieSaved
import com.claudiogalvaodev.moviemanager.data.repository.MoviesRepository
import kotlinx.coroutines.flow.collectLatest

class RemoveMovieFromMyListUseCase(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(
        movieId: Int, myListId: Int
    ): Result<Unit> {
        repository.removeMoveFromMyList(movieId, myListId)
        repository.getMoviesByMyListId(myListId).collectLatest { allMovieSaved ->
            if(allMovieSaved.isEmpty()) {
                repository.updateMyListPosterPath(myListId, "")
            } else {
                repository.updateMyListPosterPath(myListId, allMovieSaved.first().moviePosterUrl)
            }
        }
        return Result.success(Unit)
    }

}