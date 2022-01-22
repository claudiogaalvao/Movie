package com.claudiogalvaodev.moviemanager.ui.usecases

import com.claudiogalvaodev.moviemanager.model.Filter
import com.claudiogalvaodev.moviemanager.model.Movie
import com.claudiogalvaodev.moviemanager.repository.MoviesRepository
import com.claudiogalvaodev.moviemanager.utils.OrderByConstants
import com.claudiogalvaodev.moviemanager.utils.enum.FilterType

class GetMoviesByCriteriousUseCase(
    private val repository: MoviesRepository
) {

    private var currentPage = 1

    suspend operator fun invoke(
        criterious: List<Filter>,
        isUpdate: Boolean = false
    ): Result<List<Movie>?> {
        if(isUpdate) currentPage = 1
        val sortBy = (criterious.findLast { filter -> filter.type == FilterType.SORT_BY })?.currentValue ?: OrderByConstants.POPULARITY_DESC
        val withGenres = (criterious.findLast { filter -> filter.type == FilterType.GENRES })?.currentValue ?: ""

        val moviesResult = repository.getMoviesByCriterious(
            page = currentPage,
            sortBy = sortBy,
            withGenres = withGenres)
        if(moviesResult.isSuccess) {
            currentPage++
            val validMovies = removeInvalidMovies(moviesResult.getOrDefault(emptyList()))
            return Result.success(validMovies)
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