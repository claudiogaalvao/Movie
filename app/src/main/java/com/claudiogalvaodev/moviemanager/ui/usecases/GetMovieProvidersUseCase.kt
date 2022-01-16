package com.claudiogalvaodev.moviemanager.ui.usecases

import com.claudiogalvaodev.moviemanager.model.Provider
import com.claudiogalvaodev.moviemanager.repository.MoviesRepository

class GetMovieProvidersUseCase(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(movieId: Int): Result<List<Provider>?> {
        val providersResult = repository.getProviders(movieId)
        if(providersResult.isSuccess) {
            return providersResult
        }
        return providersResult
    }

}