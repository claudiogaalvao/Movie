package com.claudiogalvaodev.moviemanager.data.repository

import com.claudiogalvaodev.moviemanager.data.webclient.datasource.providers.IProvidersRemoteDatasource

class ProvidersRepository(
    private val providersRepository: IProvidersRemoteDatasource
): IProvidersRepository {
    override suspend fun getPopularProviders() = providersRepository.getPopularProviders()
}