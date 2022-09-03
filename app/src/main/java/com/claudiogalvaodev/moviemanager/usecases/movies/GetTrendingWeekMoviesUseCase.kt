package com.claudiogalvaodev.moviemanager.usecases.movies

import com.claudiogalvaodev.moviemanager.data.repository.IMoviesRepository
import com.claudiogalvaodev.moviemanager.ui.model.MovieModel
import com.claudiogalvaodev.moviemanager.data.repository.MoviesRepository
import com.claudiogalvaodev.moviemanager.utils.Constants

class GetTrendingWeekMoviesUseCase(
    private val repository: IMoviesRepository
) {

    suspend operator fun invoke(): Result<List<MovieModel>> {
        val moviesResult = repository.getTrendingWeek()
        if(moviesResult.isSuccess) {
            val movies = moviesResult.getOrDefault(emptyList())
            val filteredMovies = movies.take(Constants.MAX_TRENDING_MOVIES)
            return Result.success(filteredMovies)
        }
        return moviesResult
    }

}