package com.claudiogalvaodev.filmes.repository

import com.claudiogalvaodev.filmes.data.bd.dao.MovieDao
import com.claudiogalvaodev.filmes.data.bd.entity.MovieEntity
import com.claudiogalvaodev.filmes.webclient.service.MovieService

class MoviesRepository(
    private val dao: MovieDao,
    private val service: MovieService
) {

    suspend fun getTrendingWeek(): Result<List<MovieEntity>> {
        var result: Result<List<MovieEntity>> = Result.success(emptyList())
        try {
            val response = service.getTrendingWeek()
            if (response.isSuccessful) {
               response.body()?.results?.let { movieList ->
                   // insert on database here
                   result = Result.success(movieList)
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
                response.body()?.results?.let { movieList ->
                    // insert on database here
                    result = Result.success(movieList)
                }
            } else {
                result = Result.failure(exception = Exception("Something went wrong when try to get up coming movies"))
            }
        } catch (e: Exception) {
            result = Result.failure(exception = e)
        }
        return result
    }

    suspend fun getLatest(): Result<List<MovieEntity>> {
        var result: Result<List<MovieEntity>> = Result.success(emptyList())
        try {
            val response = service.getLatest()
            if (response.isSuccessful) {
                response.body()?.results?.let { movieList ->
                    // insert on database here
                    result = Result.success(movieList)
                }
            } else {
                result = Result.failure(exception = Exception("Something went wrong when try to get latest movies"))
            }
        } catch (e: Exception) {
            result = Result.failure(exception = e)
        }
        return result
    }

}