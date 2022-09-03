package com.claudiogalvaodev.moviemanager.usecases.movies

import com.claudiogalvaodev.moviemanager.data.repository.IMoviesRepository
import com.claudiogalvaodev.moviemanager.ui.model.MovieModel
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase

class GetMovieDetailsUseCase(
    private val repository: IMoviesRepository
) {

    suspend operator fun invoke(movieId: Int): Result<MovieModel> {
        try {
            val moviesResult = repository.getDetails(movieId)
            if(moviesResult.isSuccess) {
                val movieDetails = moviesResult.getOrDefault(null)
                if(movieDetails != null) {
                    return Result.success(movieDetails)
                }
            }
        } catch (e: Exception) {
            return sendError(message = "Error GetMovieDetailsUseCase: ${e.message}")
        }
        return sendError(message = "Something went wrong to get movie details")
    }

    private fun sendError(message: String): Result<MovieModel> {
        Firebase.crashlytics.log(message)
        return Result.failure(Exception("Do not return movie details"))
    }

}