package com.claudiogalvaodev.moviemanager.data.webclient.datasource.providers

import com.claudiogalvaodev.moviemanager.data.webclient.dto.providers.toModel
import com.claudiogalvaodev.moviemanager.data.webclient.service.MovieClient
import com.claudiogalvaodev.moviemanager.ui.model.ProviderModel

class ProvidersRemoteDatasource(
    private val movieClient: MovieClient
): IProvidersRemoteDatasource {

    override suspend fun getPopularProviders(): Result<List<ProviderModel>> {
        return try {
            val response = movieClient.getMovieProviders()
            if (response.isSuccessful) {
                val providersDto = response.body()
                Result.success(providersDto?.toModel() ?: emptyList())
            } else {
                Result.failure(Exception(""))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}