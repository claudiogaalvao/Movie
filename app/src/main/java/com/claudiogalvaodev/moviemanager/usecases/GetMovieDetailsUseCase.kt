package com.claudiogalvaodev.moviemanager.usecases

import com.claudiogalvaodev.moviemanager.data.model.Company
import com.claudiogalvaodev.moviemanager.data.repository.MoviesRepository
import com.claudiogalvaodev.moviemanager.ui.model.MovieDetailsUI
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase

class GetMovieDetailsUseCase(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(movieId: Int): Result<MovieDetailsUI> {
        try {
            val moviesResult = repository.getDetails(movieId)
            if(moviesResult.isSuccess) {
                val movieDetails = moviesResult.getOrDefault(null)
                if(movieDetails != null) {
                    val movieDetailsUI = MovieDetailsUI(
                        movie = movieDetails,
                        companies = filterCompanies(movieDetails.production_companies)
                    )
                    return Result.success(movieDetailsUI)
                }
            }
        } catch (e: Exception) {
            return sendError(message = "Error GetMovieDetailsUseCase: ${e.message}")
        }
        return sendError(message = "Something went wrong to get movie details")
    }

    private fun sendError(message: String): Result<MovieDetailsUI> {
        try {
            throw Exception(message)
        } catch (e: Exception) {
            Firebase.crashlytics.recordException(e)
        }
        return Result.failure(Exception("Do not return movie details"))
    }

    private fun filterCompanies(companies: List<Company>): List<Company> {
        return companies.filter { company -> company.logo_path != null }
    }

}