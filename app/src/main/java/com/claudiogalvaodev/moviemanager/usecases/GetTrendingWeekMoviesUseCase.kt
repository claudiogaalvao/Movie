package com.claudiogalvaodev.moviemanager.usecases

import com.claudiogalvaodev.moviemanager.data.model.Movie
import com.claudiogalvaodev.moviemanager.data.repository.MoviesRepository
import com.claudiogalvaodev.moviemanager.utils.Constants

class GetTrendingWeekMoviesUseCase(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(): Result<List<Movie>> {
        val moviesResult = repository.getTrendingWeek()
        if(moviesResult.isSuccess) {
            val validMovies = removeInvalidMovies(moviesResult.getOrDefault(emptyList()))
            val filteredMovies = validMovies.take(Constants.MAX_TRENDING_MOVIES)
            return Result.success(filteredMovies)
        }
        return moviesResult
    }

    private fun removeInvalidMovies(movies: List<Movie>): List<Movie> {
        val justMoviesWithPosterAndBackdropImage = movies.filter { movie ->
            movie.poster_path != null || movie.backdrop_path != null
        }
        return justMoviesWithPosterAndBackdropImage
    }

}