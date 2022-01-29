package com.claudiogalvaodev.moviemanager.repository

import com.claudiogalvaodev.moviemanager.model.*
import com.claudiogalvaodev.moviemanager.model.Collection
import com.claudiogalvaodev.moviemanager.webclient.service.MovieService

class MoviesRepository(
    private val service: MovieService
) {

    suspend fun getDetails(id: Int): Result<Movie?> {
        var result: Result<Movie?> = Result.success(null)
        try {
            val response = service.getDetails(id)
            if (response.isSuccessful) {
                response.body()?.let { movie ->
                    result = Result.success(movie)
                }
            } else {
                result = Result.failure(exception = Exception("Something went wrong when try to get movie details"))
            }
        } catch (e: Exception) {
            result = Result.failure(exception = e)
        }
        return result
    }

    suspend fun getCredits(movieId: Int): Result<Credits?> {
        var result: Result<Credits?> = Result.success(null)
        try {
            val response = service.getCredits(movieId)
            if (response.isSuccessful) {
                response.body()?.let { credits ->
                    result = Result.success(credits)
                }
            } else {
                result = Result.failure(exception = Exception("Something went wrong when try to get credits"))
            }
        } catch (e: Exception) {
            result = Result.failure(exception = e)
        }
        return result
    }

    suspend fun getProviders(movieId: Int): Result<List<Provider>?> {
        var result: Result<List<Provider>?>
        try {
            val response = service.getProviders(movieId)
            if (response.isSuccessful) {
                val providersBR = response.body()?.results?.get("BR")
                providersBR.let { providers ->
                    result = Result.success(providers?.flatrate)
                }
            } else {
                result = Result.failure(exception = Exception("Something went wrong when try to get credits"))
            }
        } catch (e: Exception) {
            result = Result.failure(exception = e)
        }
        return result
    }

    suspend fun getCollection(collectionId: Int): Result<Collection?> {
        var result: Result<Collection?> = Result.success(null)
        try {
            val response = service.getCollection(collectionId)
            if (response.isSuccessful) {
                response.body()?.let { collection ->
                    result = Result.success(collection)
                }
            } else {
                result = Result.failure(exception = Exception("Something went wrong when try to get credits"))
            }
        } catch (e: Exception) {
            result = Result.failure(exception = e)
        }
        return result
    }

    suspend fun getTrendingWeek(): Result<List<Movie>> {
        var result: Result<List<Movie>> = Result.success(emptyList())
        try {
            val response = service.getTrendingWeek()
            if (response.isSuccessful) {
               response.body()?.results?.let { movies ->
                   result = Result.success(movies)
               }
            } else {
                result = Result.failure(exception = Exception("Something went wrong when try to get trending movies"))
            }
        } catch (e: Exception) {
            result = Result.failure(exception = e)
        }
        return result
    }

    suspend fun getUpComing(): Result<List<Movie>> {
        var result: Result<List<Movie>> = Result.success(emptyList())
        try {
            val response = service.getUpComing()
            if (response.isSuccessful) {
                response.body()?.results?.let { movies ->
                    result = Result.success(movies)
                }
            } else {
                result = Result.failure(exception = Exception("Something went wrong when try to get up coming movies"))
            }
        } catch (e: Exception) {
            result = Result.failure(exception = e)
        }
        return result
    }

    suspend fun getPlayingNow(): Result<List<Movie>> {
        var result: Result<List<Movie>> = Result.success(emptyList())
        try {
            val response = service.getPlayingNow()
            if (response.isSuccessful) {
                response.body()?.results?.let { movies ->
                    result = Result.success(movies)
                }
            } else {
                result = Result.failure(exception = Exception("Something went wrong when try to get latest movies"))
            }
        } catch (e: Exception) {
            result = Result.failure(exception = e)
        }
        return result
    }

    suspend fun getAllGenres(): Result<List<Genre>> {
        var result: Result<List<Genre>> = Result.success(emptyList())
        try {
            val response = service.getAllGenre()
            if (response.isSuccessful) {
                response.body()?.genres?.let { genres ->
                    result = Result.success(genres)
                }
            } else {
                result = Result.failure(exception = Exception("Something went wrong when try to get genres"))
            }
        } catch (e: Exception) {
            result = Result.failure(exception = e)
        }
        return result
    }

    suspend fun getAllPopularPeople(page: Int): Result<List<Employe>> {
        var result: Result<List<Employe>> = Result.success(emptyList())
        try {
            val response = service.getAllPopularPeople(page)
            if (response.isSuccessful) {
                response.body()?.results?.let { people ->
                    result = Result.success(people)
                }
            } else {
                result = Result.failure(exception = Exception("Something went wrong when try to get popular people"))
            }
        } catch (e: Exception) {
            result = Result.failure(exception = e)
        }
        return result
    }

    suspend fun getMoviesByCriterious(page: Int, currentDate: String, sortBy: String,
                                      withGenres: String, voteCount: Int, withPeople: String,
                                      year: String
    ): Result<List<Movie>> {
        var result: Result<List<Movie>> = Result.success(emptyList())
        try {
            val response = service.getMoviesByCriterious(page, currentDate, sortBy, withGenres, voteCount, withPeople, year)
            if(response.isSuccessful) {
                response.body()?.results?.let { movies ->
                    result = Result.success(movies)
                }
            } else {
                result = Result.failure(exception = Exception("Something went wrong when try to get movies on discover"))
            }
        } catch (e: Exception) {
            result = Result.failure(exception = e)
        }
        return result
    }

}