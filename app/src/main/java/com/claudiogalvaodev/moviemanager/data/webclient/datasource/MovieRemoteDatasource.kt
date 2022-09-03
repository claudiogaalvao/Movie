package com.claudiogalvaodev.moviemanager.data.webclient.datasource

import com.claudiogalvaodev.moviemanager.data.webclient.dto.movie.*
import com.claudiogalvaodev.moviemanager.data.webclient.service.MovieClient
import com.claudiogalvaodev.moviemanager.ui.model.*
import com.claudiogalvaodev.moviemanager.utils.Constants.Companion.MAX_PLAYING_NOW_MOVIES
import com.claudiogalvaodev.moviemanager.utils.Constants.Companion.MAX_UPCOMING_MOVIES

class MovieRemoteDatasource(
    private val movieClient: MovieClient
): IMovieRemoteDatasource {

    override suspend fun getDetails(id: Int): Result<MovieModel?> {
        return try {
            val response = movieClient.getDetails(id)
            if (response.isSuccessful) {
                val movieDto = response.body()
                Result.success(movieDto?.toModel())
            } else {
                Result.failure(exception = Exception("Something went wrong when try to get movie details"))
            }
        } catch (e: Exception) {
            Result.failure(exception = e)
        }
    }

    override suspend fun getVideos(movieId: Int): Result<List<VideoModel>> {
        return try {
            val response = movieClient.getVideos(movieId)
            if (response.isSuccessful) {
                val videosDto = response.body()?.results
                Result.success(videosDto.toListOfVideosModel())
            } else {
                Result.failure(exception = Exception("Something went wrong when try to get videos"))
            }
        } catch (e: Exception) {
            Result.failure(exception = e)
        }
    }

    override suspend fun getCredits(movieId: Int): Result<CreditsModel?> {
        return try {
            val response = movieClient.getCredits(movieId)
            if (response.isSuccessful) {
                val creditsDto = response.body()
                Result.success(creditsDto?.toModel())
            } else {
                Result.failure(exception = Exception("Something went wrong when try to get credits"))
            }
        } catch (e: Exception) {
            Result.failure(exception = e)
        }
    }

    override suspend fun getProviders(movieId: Int): Result<List<ProviderModel>> {
        return try {
            val response = movieClient.getProviders(movieId)
            if (response.isSuccessful) {
                val providersBR = response.body()?.results?.get("BR")
                providersBR.let { providers ->
                    Result.success(providers?.flatrate.toListOfProviderModel())
                }
            } else {
                Result.failure(exception = Exception("Something went wrong when try to get credits"))
            }
        } catch (e: Exception) {
            Result.failure(exception = e)
        }
    }

    override suspend fun getCollection(collectionId: Int): Result<CollectionModel?> {
        return try {
            val response = movieClient.getCollection(collectionId)
            if (response.isSuccessful) {
                val collectionDto = response.body()
                Result.success(collectionDto?.toModel())
            } else {
                Result.failure(exception = Exception("Something went wrong when try to get collection"))
            }
        } catch (e: Exception) {
            Result.failure(exception = e)
        }
    }

    override suspend fun getTrendingWeek(): Result<List<MovieModel>> {
        return try {
            val response = movieClient.getTrendingWeek()
            if (response.isSuccessful) {
                val moviesDto = response.body()?.results
                Result.success(moviesDto.toListOfMoviesModel())
            } else {
                Result.failure(exception = Exception("Something went wrong when try to get trending movies"))
            }
        } catch (e: Exception) {
            Result.failure(exception = e)
        }
    }

    override suspend fun getHighlights(): Result<HighlightsModel> {
        val allMovies = mutableListOf<MovieDto>()
        val upComingResult = getUpComing()
        val playingNowResult = getPlayingNow()
        if (upComingResult.isSuccess || playingNowResult.isSuccess) {
            allMovies.addAll(upComingResult.getOrDefault(emptyList()))
            allMovies.addAll(playingNowResult.getOrDefault(emptyList()))

            val highlights = HighlightsModel(
                upComing = allMovies
                    .getJustUpComingMovies()
                    .orderMoviesByAscendingReleaseDate()
                    .toListOfMoviesModel()
                    .take(MAX_UPCOMING_MOVIES),
                playingNow = allMovies
                    .getJustPlayingNowMovies()
                    .orderMoviesByDescendingReleaseDate()
                    .toListOfMoviesModel()
                    .take(MAX_PLAYING_NOW_MOVIES)
            )

            return Result.success(highlights)
        }
        return Result.failure(exception = Exception("Something went wrong when try to get highlights"))
    }

    private suspend fun getUpComing(): Result<List<MovieDto>> {
        return try {
            val response = movieClient.getUpComing()
            if (response.isSuccessful) {
                val moviesDto = response.body()?.results  ?: emptyList()
                Result.success(moviesDto)
            } else {
                Result.failure(exception = Exception("Something went wrong when try to get up coming movies"))
            }
        } catch (e: Exception) {
            Result.failure(exception = e)
        }
    }

    private suspend fun getPlayingNow(): Result<List<MovieDto>> {
        return try {
            val response = movieClient.getPlayingNow()
            if (response.isSuccessful) {
                val moviesDto = response.body()?.results ?: emptyList()
                Result.success(moviesDto)
            } else {
                Result.failure(exception = Exception("Something went wrong when try to get latest movies"))
            }
        } catch (e: Exception) {
            Result.failure(exception = e)
        }
    }

    override suspend fun getAllGenres(): Result<List<GenreModel>> {
        return try {
            val response = movieClient.getAllGenre()
            if (response.isSuccessful) {
                val genresDto = response.body()?.genres
                Result.success(genresDto.toListOfGenreModel())
            } else {
                Result.failure(exception = Exception("Something went wrong when try to get genres"))
            }
        } catch (e: Exception) {
            Result.failure(exception = e)
        }
    }

    override suspend fun getAllPopularPeople(page: Int): Result<List<PersonModel>> {
        return try {
            val response = movieClient.getAllPopularPeople(page)
            if (response.isSuccessful) {
                val peopleDto = response.body()?.results
                Result.success(peopleDto.toListOfPersonModel())
            } else {
                Result.failure(exception = Exception("Something went wrong when try to get popular people"))
            }
        } catch (e: Exception) {
            Result.failure(exception = e)
        }
    }

    override suspend fun getPersonDetails(personId: Int): Result<PersonModel?> {
        return try {
            val response = movieClient.getPersonDetails(personId.toString())
            if (response.isSuccessful) {
                val personDto = response.body()
                Result.success(personDto?.toModel())
            } else {
                Result.failure(exception = Exception("Something went wrong when try to get person details"))
            }
        } catch (e: Exception) {
            Result.failure(exception = e)
        }
    }

    override suspend fun getMoviesByCriterion(
        page: Int, currentDate: String, sortBy: String,
        withGenres: String, voteCount: Int, withPeople: String, year: String,
    ): Result<List<MovieModel>> {
        return try {
            val response = movieClient
                .getMoviesByCriterious(page, currentDate, sortBy, withGenres, voteCount, withPeople, year)
            if(response.isSuccessful) {
                val moviesDto = response.body()?.results
                Result.success(moviesDto.toListOfMoviesModel())
            } else {
                Result.failure(exception = Exception("Something went wrong when try to get movies on discover"))
            }
        } catch (e: Exception) {
            Result.failure(exception = e)
        }
    }

    override suspend fun searchMovie(page: Int, query: String): Result<List<MovieModel>> {
        return try {
            val response = movieClient.searchMovie(page, query)
            if(response.isSuccessful) {
                val moviesDto = response.body()?.results
                Result.success(moviesDto.toListOfMoviesModel())
            } else {
                Result.failure(exception = Exception("Something went wrong when try to search movies"))
            }
        } catch (e: Exception) {
            Result.failure(exception = e)
        }
    }

    override suspend fun searchPeople(page: Int, query: String): Result<List<PersonModel>> {
        return try {
            val response = movieClient.searchPerson(page, query)
            if(response.isSuccessful) {
                val peopleDto = response.body()?.results
                Result.success(peopleDto.toListOfPersonModel())
            } else {
                Result.failure(exception = Exception("Something went wrong when try to search people"))
            }
        } catch (e: Exception) {
            Result.failure(exception = e)
        }
    }

}