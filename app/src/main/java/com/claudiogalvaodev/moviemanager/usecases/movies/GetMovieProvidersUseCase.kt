package com.claudiogalvaodev.moviemanager.usecases.movies

import com.claudiogalvaodev.moviemanager.data.repository.IMoviesRepository
import com.claudiogalvaodev.moviemanager.ui.model.ProviderModel

class GetMovieProvidersUseCase(
    private val repository: IMoviesRepository
) {

    suspend operator fun invoke(movieId: Int): Result<List<ProviderModel>?> {
        val providersResult = repository.getProviders(movieId)

        if (providersResult.isSuccess) {
            val providers = providersResult.getOrDefault(emptyList())
            val filteredProviders = removeUnknownProviders(providers)
            return Result.success(filteredProviders)
        }
        return providersResult
    }

    private fun removeUnknownProviders(providers: List<ProviderModel>?): List<ProviderModel> {
        return providers?.let {
            it.filter { provider ->
                !UNKNOWN_PROVIDERS.contains(provider.name)
            }
        } ?: emptyList()
    }

    companion object {
        private val UNKNOWN_PROVIDERS = listOf("NOW", "Oi Play")
    }

}