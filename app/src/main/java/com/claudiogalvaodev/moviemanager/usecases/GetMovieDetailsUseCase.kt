package com.claudiogalvaodev.moviemanager.usecases

import com.claudiogalvaodev.moviemanager.data.model.Company
import com.claudiogalvaodev.moviemanager.data.repository.MoviesRepository
import com.claudiogalvaodev.moviemanager.ui.model.MovieDetailsUI

class GetMovieDetailsUseCase(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(movieId: Int): Result<MovieDetailsUI> {
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
        return Result.failure(Exception("Do not return movie details"))
    }

    private fun filterCompanies(companies: List<Company>): List<Company> {
        return companies.filter { company -> company.logo_path != null }
    }

}