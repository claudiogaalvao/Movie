package com.claudiogalvaodev.moviemanager.repository

import com.claudiogalvaodev.moviemanager.data.bd.dao.MovieDao
import com.claudiogalvaodev.moviemanager.data.bd.entity.MovieEntity
import com.claudiogalvaodev.moviemanager.webclient.service.MovieService
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MoviesRepository(
    private val dao: MovieDao,
    private val service: MovieService
) {

    suspend fun getTrendingWeek(): Result<List<MovieEntity>> {
        var result: Result<List<MovieEntity>> = Result.success(emptyList())
        try {
            val response = service.getTrendingWeek()
            if (response.isSuccessful) {
               response.body()?.results?.let { movies ->
                   val filteredMovies = removeInvalidMovies(movies)
                   result = Result.success(filteredMovies)
               }
            } else {
                result = Result.failure(exception = Exception("Something went wrong when try to get trending movies"))
            }
        } catch (e: Exception) {
            result = Result.failure(exception = e)
        }
        return result
    }

    suspend fun getUpComing(): Result<List<MovieEntity>> {
        var result: Result<List<MovieEntity>> = Result.success(emptyList())
        try {
            val response = service.getUpComing()
            if (response.isSuccessful) {
                response.body()?.results?.let { movies ->
                    val filteredMovies = removeInvalidMovies(movies)
                    val orderedMovies =
                        orderMoviesByAscendingRelease(filteredMovies)
                    result = Result.success(orderedMovies)
                }
            } else {
                result = Result.failure(exception = Exception("Something went wrong when try to get up coming movies"))
            }
        } catch (e: Exception) {
            result = Result.failure(exception = e)
        }
        return result
    }

    suspend fun getPlayingNow(): Result<List<MovieEntity>> {
        var result: Result<List<MovieEntity>> = Result.success(emptyList())
        try {
            val response = service.getPlayingNow()
            if (response.isSuccessful) {
                response.body()?.results?.let { movies ->
                    val filteredMovies = removeInvalidMovies(movies)
                    val orderedMovies =
                        orderMoviesByDescendingRelease(filteredMovies)
                    result = Result.success(orderedMovies)
                }
            } else {
                result = Result.failure(exception = Exception("Something went wrong when try to get latest movies"))
            }
        } catch (e: Exception) {
            result = Result.failure(exception = e)
        }
        return result
    }

    private fun removeInvalidMovies(movies: List<MovieEntity>): List<MovieEntity> {
        val justMoviesWithPosterAndBackdropImage = movies.filter { movie ->
            movie.poster_path != null || movie.backdrop_path != null
        }
        return justMoviesWithPosterAndBackdropImage
    }

    private fun orderMoviesByAscendingRelease(movies: List<MovieEntity>): List<MovieEntity> {
        val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val descendingOrder = movies.sortedByDescending { movie ->
            LocalDate.parse(movie.release_date, dateTimeFormatter)
        }
        return descendingOrder.reversed()
    }

    private fun orderMoviesByDescendingRelease(movies: List<MovieEntity>): List<MovieEntity> {
        val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return movies.sortedByDescending { movie ->
            LocalDate.parse(movie.release_date, dateTimeFormatter)
        }
    }

}