package com.claudiogalvaodev.moviemanager.ui.usecases

import com.claudiogalvaodev.moviemanager.model.Movie
import com.claudiogalvaodev.moviemanager.repository.MoviesRepository
import com.claudiogalvaodev.moviemanager.utils.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class GetUpComingAndPlayingNowMoviesUseCase(
    private val repository: MoviesRepository,
) {
    private val _upComingMovies = MutableStateFlow<List<Movie>>(emptyList())
    val upComingMovies = _upComingMovies.asStateFlow()

    private val _playingNowMovies = MutableStateFlow<List<Movie>>(emptyList())
    val playingNowMovies = _playingNowMovies.asStateFlow()

    private var upComingMoviesList: MutableList<Movie> = mutableListOf()
    private var playingNowMoviesList: MutableList<Movie> = mutableListOf()

    suspend operator fun invoke() {
        val upComingResult = repository.getUpComing()
        val playingNowResult = repository.getPlayingNow()

        if(upComingResult.isSuccess && playingNowResult.isSuccess) {
            filterUpComingFromNowPlayingMovies(playingNowResult.getOrDefault(emptyList()))
            filterNowPlayingFromUpComingMovies(upComingResult.getOrDefault(emptyList()))

            // UpComing
            val filteredUpComingMovies = removeInvalidMovies(upComingMoviesList)
            val orderedUpComingMovies = orderMoviesByMostCloseDateFirstAndThenNextDates(filteredUpComingMovies)
            _upComingMovies.value = orderedUpComingMovies.take(
                Constants.MAX_UPCOMING_MOVIES
            )

            // Playing Now
            val filteredPlayingNowMovies = removeInvalidMovies(playingNowMoviesList)
            val orderedPlayingNowMovies = orderMoviesByMostCloseDateFirstAndThenBeforeDates(filteredPlayingNowMovies)
            _playingNowMovies.value = orderedPlayingNowMovies.take(
                Constants.MAX_LATEST_MOVIES
            )
        }
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

    private fun orderMoviesByMostCloseDateFirstAndThenNextDates(movies: List<Movie>): List<Movie> {
        val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val descendingOrder = movies.sortedByDescending { movie ->
            LocalDate.parse(movie.release_date, dateTimeFormatter)
        }
        return descendingOrder.reversed()
    }

    private fun orderMoviesByMostCloseDateFirstAndThenBeforeDates(movies: List<Movie>): List<Movie> {
        val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return movies.sortedByDescending { movie ->
            LocalDate.parse(movie.release_date, dateTimeFormatter)
        }
    }

}