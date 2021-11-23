package com.claudiogalvaodev.filmes.webclient.service

import com.claudiogalvaodev.filmes.model.RequestCallback
import retrofit2.Response
import retrofit2.http.GET

interface MovieService {

    // TODO Utilizar o Pro Guard para ofuscar a api_key, criando uma biblioteca nativa dentro do Kotlin
    @GET("movie/popular?api_key=696a8a555845ed882929281feba3dabf&page=1&language=pt-BR")
    suspend fun getPopularMovies(): Response<RequestCallback>
}