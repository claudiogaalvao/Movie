package com.claudiogalvaodev.moviemanager.usecases.movies

import com.claudiogalvaodev.moviemanager.data.repository.IMoviesRepository
import com.claudiogalvaodev.moviemanager.ui.model.CreditsModel

class GetMovieCreditsUseCase(
    private val repository: IMoviesRepository
) {

    suspend operator fun invoke(movieId: Int): Result<CreditsModel?> {
        val creditsResult = repository.getCredits(movieId)
        if(creditsResult.isSuccess) {
            val movieCredits = creditsResult.getOrDefault(null)
            if(movieCredits != null) {
                return Result.success(movieCredits)
            }
        }
        return creditsResult
    }

}