package com.claudiogalvaodev.moviemanager.repository

import com.claudiogalvaodev.moviemanager.model.Collection
import com.claudiogalvaodev.moviemanager.model.Credits
import com.claudiogalvaodev.moviemanager.model.Employe
import com.claudiogalvaodev.moviemanager.model.Movie
import com.claudiogalvaodev.moviemanager.model.Provider
import com.claudiogalvaodev.moviemanager.utils.constants.MaxMoviesToShow
import com.claudiogalvaodev.moviemanager.webclient.service.MovieService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MoviesRepository(
    private val service: MovieService
) {

    private val _upComingMovies = MutableStateFlow<List<Movie>>(emptyList())
    val upComingMovies = _upComingMovies.asStateFlow()

    private val _playingNowMovies = MutableStateFlow<List<Movie>>(emptyList())
    val playingNowMovies = _playingNowMovies.asStateFlow()

    private var upComingMoviesList: MutableList<Movie> = mutableListOf()
    private var playingNowMoviesList: MutableList<Movie> = mutableListOf()

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
                   val filteredMovies = removeInvalidMovies(movies.take(MaxMoviesToShow.MAX_TRENDING_MOVIES))
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

    suspend fun updateUpComingAndPlayingNow() {
        val upComingMoviesResult = getUpComing()
        val playingNowMoviesResult = getPlayingNow()

        if(upComingMoviesResult.isSuccess && playingNowMoviesResult.isSuccess) {
            filterUpComingFromNowPlayingMovies(playingNowMoviesResult.getOrDefault(emptyList()))
            filterNowPlayingFromUpComingMovies(upComingMoviesResult.getOrDefault(emptyList()))

            // UpComing
            val filteredUpComingMovies = removeInvalidMovies(upComingMoviesList)
            val orderedUpComingMovies = orderMoviesByAscendingRelease(filteredUpComingMovies)
            _upComingMovies.value = orderedUpComingMovies.take(
                MaxMoviesToShow.MAX_UPCOMING_MOVIES
            )

            // Playing Now
            val filteredPlayingNowMovies = removeInvalidMovies(playingNowMoviesList)
            val orderedPlayingNowMovies = orderMoviesByDescendingRelease(filteredPlayingNowMovies)
            _playingNowMovies.value = orderedPlayingNowMovies.take(
                MaxMoviesToShow.MAX_LATEST_MOVIES
            )
        }

    }

    private suspend fun getUpComing(): Result<List<Movie>> {
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

    private suspend fun getPlayingNow(): Result<List<Movie>> {
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

    private fun removeInvalidMovies(movies: List<Movie>): List<Movie> {
        val justMoviesWithPosterAndBackdropImage = movies.filter { movie ->
            movie.poster_path != null || movie.backdrop_path != null
        }
        return justMoviesWithPosterAndBackdropImage
    }

    private fun filterUpComingFromNowPlayingMovies(nowPlayingMovies: List<Movie>) {
        val currentDate = LocalDate.now()
        nowPlayingMovies.filter { movie ->
            val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val releaseDate = LocalDate.parse(movie.release_date, dateTimeFormatter)
            if(releaseDate.isAfter(currentDate)) {
                upComingMoviesList.add(movie)
            } else {
                playingNowMoviesList.add(movie)
            }
        }
    }

    private fun filterNowPlayingFromUpComingMovies(upComingMovies: List<Movie>) {
        val currentDate = LocalDate.now()
        upComingMovies.filter { movie ->
            val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val releaseDate = LocalDate.parse(movie.release_date, dateTimeFormatter)
            if(releaseDate.isEqual(currentDate) || releaseDate.isBefore(currentDate)) {
                playingNowMoviesList.add(movie)
            } else {
                upComingMoviesList.add(movie)
            }
        }
    }

    private fun orderMoviesByAscendingRelease(movies: List<Movie>): List<Movie> {
        val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val descendingOrder = movies.sortedByDescending { movie ->
            LocalDate.parse(movie.release_date, dateTimeFormatter)
        }
        return descendingOrder.reversed()
    }

    private fun orderMoviesByDescendingRelease(movies: List<Movie>): List<Movie> {
        val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return movies.sortedByDescending { movie ->
            LocalDate.parse(movie.release_date, dateTimeFormatter)
        }
    }

}