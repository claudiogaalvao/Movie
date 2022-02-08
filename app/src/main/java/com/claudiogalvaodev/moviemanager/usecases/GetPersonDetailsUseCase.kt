package com.claudiogalvaodev.moviemanager.usecases

import com.claudiogalvaodev.moviemanager.data.model.Employe
import com.claudiogalvaodev.moviemanager.data.repository.MoviesRepository

class GetPersonDetailsUseCase(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(personId: String): Result<Employe?> {
        return repository.getPersonDetails(personId)
    }

}