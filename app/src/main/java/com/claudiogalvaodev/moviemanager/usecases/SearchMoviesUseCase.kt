package com.claudiogalvaodev.moviemanager.usecases

import com.claudiogalvaodev.moviemanager.data.model.Movie
import com.claudiogalvaodev.moviemanager.data.repository.MoviesRepository

class SearchMoviesUseCase(
    private val repository: MoviesRepository
) {

    private var currentPage = 1

    suspend operator fun invoke(
        query: String,
        isUpdate: Boolean = false
    ): Result<List<Movie>> {
        if(isUpdate) currentPage = 1
        val moviesResult = repository.searchMovie(currentPage, query)

        if(moviesResult.isSuccess) {
            currentPage++
            val validMovies = removeInvalidMovies(moviesResult.getOrDefault(emptyList()))
            return Result.success(validMovies)
        }
        return moviesResult
    }

    private fun removeInvalidMovies(movies: List<Movie>): List<Movie> {
        val justMoviesWithPosterAndBackdropImage = movies.filter { movie ->
            movie.poster_path.isBlank() || movie.backdrop_path.isBlank()
        }
        return justMoviesWithPosterAndBackdropImage
    }

}