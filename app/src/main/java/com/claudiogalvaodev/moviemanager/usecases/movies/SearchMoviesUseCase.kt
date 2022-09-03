package com.claudiogalvaodev.moviemanager.usecases.movies

import com.claudiogalvaodev.moviemanager.ui.model.MovieModel
import com.claudiogalvaodev.moviemanager.data.repository.MoviesRepository

class SearchMoviesUseCase(
    private val repository: MoviesRepository
) {

    // TODO Controlo a página por aqui mesmo ou pelo viewmodel?
    private var currentPage = 1

    suspend operator fun invoke(
        query: String,
        isUpdate: Boolean = false
    ): Result<List<MovieModel>> {
        if(isUpdate) currentPage = 1
        val moviesResult = repository.searchMovie(currentPage, query)

        if(moviesResult.isSuccess) {
            currentPage++
            val movies = moviesResult.getOrDefault(emptyList())
            return Result.success(movies)
        }
        return moviesResult
    }

}