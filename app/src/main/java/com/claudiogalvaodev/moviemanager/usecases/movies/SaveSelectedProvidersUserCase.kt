package com.claudiogalvaodev.moviemanager.usecases.movies

import com.claudiogalvaodev.moviemanager.Provider
import com.claudiogalvaodev.moviemanager.data.datastore.IUserPreferencesRepository

class SaveSelectedProvidersUserCase(
    private val userPreferencesRepository: IUserPreferencesRepository
) {
    suspend operator fun invoke(selectedProviders: List<Provider>) {
        userPreferencesRepository.saveSelectedProviders(selectedProviders)
    }
}