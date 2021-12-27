package com.claudiogalvaodev.moviemanager.webclient.service

import com.claudiogalvaodev.moviemanager.model.*
import com.claudiogalvaodev.moviemanager.model.Collection
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService {

    @GET("trending/movie/week")
    suspend fun getTrendingWeek(): Response<RequestCallback>

    @GET("movie/upcoming")
    suspend fun getUpComing(): Response<RequestCallback>

    @GET("movie/now_playing")
    suspend fun getPlayingNow(): Response<RequestCallback>

    @GET("movie/{id}")
    suspend fun getDetails(@Path("id") id: Int): Response<Movie>

    @GET("movie/{id}/credits")
    suspend fun getCredits(@Path("id") id: Int): Response<Credits>

    @GET("movie/{id}/watch/providers")
    suspend fun getProviders(@Path("id") id: Int): Response<ProvidersResponse>

    @GET("collection/{id}")
    suspend fun getCollection(@Path("id") id: Int): Response<Collection>
}