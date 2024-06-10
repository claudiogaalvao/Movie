package com.claudiogalvaodev.moviemanager.data.repository

import com.claudiogalvaodev.moviemanager.ui.model.ProviderModel

interface IProvidersRepository {

    suspend fun getPopularProviders(): Result<List<ProviderModel>>

}