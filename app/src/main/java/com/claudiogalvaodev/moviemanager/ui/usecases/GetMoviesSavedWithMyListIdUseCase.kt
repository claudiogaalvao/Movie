package com.claudiogalvaodev.moviemanager.ui.usecases

import com.claudiogalvaodev.moviemanager.data.repository.MoviesRepository

class GetMoviesSavedWithMyListIdUseCase(
    private val repository: MoviesRepository
) {

    fun invoke(myListId: Int) = repository.getMoviesSavedWithMyListId(myListId)

}