package com.claudiogalvaodev.moviemanager.ui.usecases

import android.util.Log
import com.claudiogalvaodev.moviemanager.model.Filter
import com.claudiogalvaodev.moviemanager.model.Movie
import com.claudiogalvaodev.moviemanager.repository.MoviesRepository
import com.claudiogalvaodev.moviemanager.utils.OrderByConstants
import com.claudiogalvaodev.moviemanager.utils.enum.FilterType
import java.time.LocalDate

class GetMoviesByCriteriousUseCase(
    private val repository: MoviesRepository
) {

    private var currentPage = 1

    suspend operator fun invoke(
        criterious: List<Filter>,
        isUpdate: Boolean = false
    ): Result<List<Movie>?> {
        val currentDate = LocalDate.now()
        if(isUpdate) currentPage = 1
        val sortBy = (criterious.find { filter -> filter.type == FilterType.SORT_BY })?.currentValue ?: OrderByConstants.POPULARITY_DESC
        val withGenres = (criterious.find { filter -> filter.type == FilterType.GENRES })?.currentValue ?: ""
        val withPeople = (criterious.find { filter -> filter.type == FilterType.PEOPLE })?.currentValue ?: ""

        val voteCount = if(sortBy == OrderByConstants.VOTE_AVERAGE_DESC) 1000 else 0

        val moviesResult = repository.getMoviesByCriterious(
            page = currentPage,
            currentDate = currentDate.toString(),
            sortBy = sortBy,
            withGenres = withGenres,
            voteCount = voteCount,
            withPeople = withPeople)
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