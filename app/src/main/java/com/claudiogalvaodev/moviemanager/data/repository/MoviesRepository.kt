package com.claudiogalvaodev.moviemanager.data.repository

import com.claudiogalvaodev.moviemanager.data.webclient.datasource.movie.IMovieRemoteDatasource
import com.claudiogalvaodev.moviemanager.ui.model.*

class MoviesRepository(
    private val movieRemoteDatasource: IMovieRemoteDatasource
): IMoviesRepository {

    // Home
    override suspend fun getTrendingWeek(): Result<List<MovieModel>> =
        movieRemoteDatasource.getTrendingWeek()

    override suspend fun getHighlights(): Result<HighlightsModel> =
        movieRemoteDatasource.getHighlights()

    // Movie Details
    override suspend fun getDetails(id: Int): Result<MovieModel?> =
        movieRemoteDatasource.getDetails(id)

    override suspend fun getVideos(movieId: Int): Result<List<VideoModel>> =
        movieRemoteDatasource.getVideos(movieId)

    override suspend fun getCredits(movieId: Int): Result<CreditsModel?> =
        movieRemoteDatasource.getCredits(movieId)

    override suspend fun getProviders(movieId: Int): Result<List<ProviderModel>> =
        movieRemoteDatasource.getProviders(movieId)

    override suspend fun getCollection(collectionId: Int): Result<CollectionModel?> =
        movieRemoteDatasource.getCollection(collectionId)

    // References
    override suspend fun getAllGenres(): Result<List<GenreModel>> =
        movieRemoteDatasource.getAllGenres()

    // People
    override suspend fun getAllPopularPeople(page: Int): Result<List<PersonModel>> =
        movieRemoteDatasource.getAllPopularPeople(page)

    override suspend fun getPersonDetails(personId: Int): Result<PersonModel?> =
        movieRemoteDatasource.getPersonDetails(personId)

    // Search
    override suspend fun getMoviesByCriterion(
        page: Int,
        currentDate: String,
        sortBy: String,
        withGenres: String,
        voteCount: Int,
        withPeople: String,
        year: String
    ): Result<List<MovieModel>> = movieRemoteDatasource.getMoviesByCriterion(
        page = page,
        currentDate = currentDate,
        sortBy = sortBy,
        withGenres = withGenres,
        voteCount = voteCount,
        withPeople = withPeople,
        year = year
    )

    override suspend fun searchMovie(page: Int, query: String): Result<List<MovieModel>> =
        movieRemoteDatasource.searchMovie(page = page, query = query)

    override suspend fun searchPeople(page: Int, query: String): Result<List<PersonModel>> =
        movieRemoteDatasource.searchPeople(page = page, query = query)


}