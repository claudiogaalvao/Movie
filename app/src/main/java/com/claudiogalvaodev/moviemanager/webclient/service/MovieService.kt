package com.claudiogalvaodev.moviemanager.webclient.service

import com.claudiogalvaodev.moviemanager.model.RequestCallback
import retrofit2.Response
import retrofit2.http.GET

interface MovieService {

    @GET("trending/movie/week")
    suspend fun getTrendingWeek(): Response<RequestCallback>

    @GET("movie/upcoming")
    suspend fun getUpComing(): Response<RequestCallback>

    @GET("movie/now_playing")
    suspend fun getLatest(): Response<RequestCallback>
}