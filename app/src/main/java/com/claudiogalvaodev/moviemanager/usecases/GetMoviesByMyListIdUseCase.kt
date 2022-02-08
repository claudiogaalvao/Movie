package com.claudiogalvaodev.moviemanager.usecases

import com.claudiogalvaodev.moviemanager.data.repository.MoviesRepository

class GetMoviesByMyListIdUseCase(
    private val repository: MoviesRepository
) {

    fun invoke(myListId: Int) = repository.getMoviesByMyListId(myListId)

}