package com.claudiogalvaodev.moviemanager.usecases.movies

import com.claudiogalvaodev.moviemanager.data.repository.IProvidersRepository
import com.claudiogalvaodev.moviemanager.ui.model.ProviderModel

private const val MAX_PROVIDERS = 13

class GetPopularProvidersAndUserSelectionUseCase(
    private val repository: IProvidersRepository
) {
    suspend operator fun invoke(): Result<List<ProviderModel>> {
        // TODO Check user preferences
        val result = repository.getPopularProviders()
        result.getOrNull()?.let { providers ->
            return Result.success(providers.take(MAX_PROVIDERS))
        }
        return Result.failure(Exception("Error getting popular providers"))
    }
}