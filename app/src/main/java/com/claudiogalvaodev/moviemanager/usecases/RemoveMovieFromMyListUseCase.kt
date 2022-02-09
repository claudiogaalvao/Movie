package com.claudiogalvaodev.moviemanager.usecases

import com.claudiogalvaodev.moviemanager.data.bd.entity.MovieSaved
import com.claudiogalvaodev.moviemanager.data.repository.MoviesRepository
import kotlinx.coroutines.flow.collectLatest

class RemoveMovieFromMyListUseCase(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(
        movieSaved: MovieSaved
    ): Result<Unit> {
        repository.removeMoveFromMyList(movieSaved)
        repository.getMoviesByMyListId(movieSaved.myListId).collectLatest { allMovieSaved ->
            if(allMovieSaved.isEmpty()) {
                repository.updateMyListPosterPath(movieSaved.myListId, "")
            } else {
                repository.updateMyListPosterPath(movieSaved.myListId, allMovieSaved.first().moviePosterUrl)
            }
        }
        return Result.success(Unit)
    }

}