package com.claudiogalvaodev.moviemanager.webclient.service

import com.claudiogalvaodev.moviemanager.model.*
import com.claudiogalvaodev.moviemanager.model.Collection
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("trending/movie/week")
    suspend fun getTrendingWeek(): Response<MoviesResponse>

    @GET("movie/upcoming")
    suspend fun getUpComing(): Response<MoviesResponse>

    @GET("movie/now_playing")
    suspend fun getPlayingNow(@Query("page") page: Int = 1): Response<MoviesResponse>

    @GET("movie/{id}")
    suspend fun getDetails(@Path("id") id: Int): Response<Movie>

    @GET("movie/{id}/credits")
    suspend fun getCredits(@Path("id") id: Int): Response<Credits>

    @GET("movie/{id}/watch/providers")
    suspend fun getProviders(@Path("id") id: Int): Response<ProvidersResponse>

    @GET("collection/{id}")
    suspend fun getCollection(@Path("id") id: Int): Response<Collection>

    @GET("genre/movie/list")
    suspend fun getAllGenre(): Response<GenresResponse>

    @GET("person/popular")
    suspend fun getAllPopularPeople(@Query("page") page: Int): Response<EmployeResponse>

    @GET("discover/movie")
    suspend fun getMoviesByCriterious(
        @Query("page") page: Int,
        @Query("release_date.lte") currentDate: String,
        @Query("sort_by") sortBy: String,
        @Query("with_genres") withGenres: String,
        @Query("vote_count.gte") voteCount: Int
    ): Response<MoviesResponse>
}