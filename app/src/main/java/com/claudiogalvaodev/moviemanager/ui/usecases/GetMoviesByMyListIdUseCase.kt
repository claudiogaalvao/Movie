package com.claudiogalvaodev.moviemanager.ui.usecases

import com.claudiogalvaodev.moviemanager.data.model.Movie
import com.claudiogalvaodev.moviemanager.data.repository.MoviesRepository

class GetMoviesByMyListIdUseCase(
    private val repository: MoviesRepository
) {

    fun invoke(myListId: Int) = repository.getMoviesByMyListId(myListId)

}