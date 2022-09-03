package com.claudiogalvaodev.moviemanager.data.webclient.datasource

import com.claudiogalvaodev.moviemanager.ui.model.*

interface IMovieRemoteDatasource {

    suspend fun getDetails(id: Int): Result<MovieModel?>

    suspend fun getVideos(movieId: Int): Result<List<VideoModel>>

    suspend fun getCredits(movieId: Int): Result<CreditsModel?>

    suspend fun getProviders(movieId: Int): Result<List<ProviderModel>>

    suspend fun getCollection(collectionId: Int): Result<CollectionModel?>

    suspend fun getTrendingWeek(): Result<List<MovieModel>>

    suspend fun getHighlights(): Result<HighlightsModel>

    suspend fun getAllGenres(): Result<List<GenreModel>>

    suspend fun getAllPopularPeople(page: Int): Result<List<PersonModel>>

    suspend fun getPersonDetails(personId: Int): Result<PersonModel?>

    suspend fun getMoviesByCriterion(
        page: Int,
        currentDate: String,
        sortBy: String,
        withGenres: String,
        voteCount: Int,
        withPeople: String,
        year: String
    ): Result<List<MovieModel>>

    suspend fun searchMovie(page: Int, query: String): Result<List<MovieModel>>

    suspend fun searchPeople(page: Int, query: String): Result<List<PersonModel>>
}