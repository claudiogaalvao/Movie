package com.claudiogalvaodev.moviemanager.usecases

import com.claudiogalvaodev.moviemanager.data.model.Provider
import com.claudiogalvaodev.moviemanager.data.repository.MoviesRepository

class GetMovieProvidersUseCase(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(movieId: Int): Result<List<Provider>?> {
        val providersResult = repository.getProviders(movieId)

        if (providersResult.isSuccess) {
            val providers = providersResult.getOrDefault(emptyList())

            val filteredProviders = removeUnknownProviders(providers)
            return Result.success(filteredProviders)
        }
        return providersResult
    }

    private fun removeUnknownProviders(providers: List<Provider>?): List<Provider> {
        return providers?.let {
            it.filter { provider -> provider.provider_name != UNKNOWN_PROVIDERS[0] && provider.provider_name != UNKNOWN_PROVIDERS[1] }
        } ?: emptyList()
    }

    companion object {
        private val UNKNOWN_PROVIDERS = listOf("NOW", "Oi Play")
    }

}