package com.claudiogalvaodev.moviemanager.webclient.service

import com.claudiogalvaodev.moviemanager.model.RequestCallback
import retrofit2.Response
import retrofit2.http.GET

interface MovieService {

    @GET("trending/movie/week?language=pt-BR&region=BR")
    suspend fun getTrendingWeek(): Response<RequestCallback>

    @GET("movie/upcoming?language=pt-BR&region=BR")
    suspend fun getUpComing(): Response<RequestCallback>

    @GET("movie/now_playing?language=pt-BR&region=BR")
    suspend fun getLatest(): Response<RequestCallback>
}