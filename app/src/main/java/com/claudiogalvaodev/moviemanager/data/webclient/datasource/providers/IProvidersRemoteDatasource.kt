package com.claudiogalvaodev.moviemanager.data.webclient.datasource.providers

import com.claudiogalvaodev.moviemanager.ui.model.ProviderModel


interface IProvidersRemoteDatasource {

    suspend fun getMovieProviders(): Result<List<ProviderModel>>

}