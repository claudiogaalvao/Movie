package com.claudiogalvaodev.moviemanager.ui.usecases

import com.claudiogalvaodev.moviemanager.data.model.Provider
import com.claudiogalvaodev.moviemanager.data.repository.MoviesRepository

class GetMovieProvidersUseCase(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(movieId: Int): Result<List<Provider>?> {
        return repository.getProviders(movieId)
    }

}