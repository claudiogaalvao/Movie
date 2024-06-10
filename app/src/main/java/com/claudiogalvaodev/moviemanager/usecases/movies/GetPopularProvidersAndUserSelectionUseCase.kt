package com.claudiogalvaodev.moviemanager.usecases.movies

import com.claudiogalvaodev.moviemanager.data.datastore.IUserPreferencesRepository
import com.claudiogalvaodev.moviemanager.data.datastore.UserPreferencesRepository
import com.claudiogalvaodev.moviemanager.data.repository.IProvidersRepository
import com.claudiogalvaodev.moviemanager.ui.model.ProviderModel

private const val MAX_PROVIDERS = 13

class GetPopularProvidersAndUserSelectionUseCase(
    private val repository: IProvidersRepository,
    private val userPreferencesRepository: IUserPreferencesRepository
) {
    suspend operator fun invoke(): Result<List<ProviderModel>> {
        // TODO Check user preferences
        val selectedProviders = userPreferencesRepository.getSelectedProviders().value
        val result = repository.getPopularProviders()
        result.getOrNull()?.let { providers ->
            providers.take(MAX_PROVIDERS).map { provider ->
                provider.copy(isSelected = selectedProviders?.any { it.id == provider.id } ?: false)
            }
            return Result.success(providers)
        }
        return Result.failure(Exception("Error getting popular providers"))
    }
}