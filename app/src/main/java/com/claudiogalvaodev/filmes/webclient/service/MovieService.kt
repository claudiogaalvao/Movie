package com.claudiogalvaodev.filmes.webclient.service

import com.claudiogalvaodev.filmes.model.RequestCallback
import retrofit2.Response
import retrofit2.http.GET

interface MovieService {

    // TODO Utilizar o Pro Guard para ofuscar a api_key, criando uma biblioteca nativa dentro do Kotlin
    @GET("trending/movie/week?language=pt-BR&region=BR")
    suspend fun getTrendingWeek(): Response<RequestCallback>

    @GET("movie/upcoming?language=pt-BR&region=BR")
    suspend fun getUpComing(): Response<RequestCallback>

    @GET("movie/now_playing?language=pt-BR&region=BR")
    suspend fun getLatest(): Response<RequestCallback>
}