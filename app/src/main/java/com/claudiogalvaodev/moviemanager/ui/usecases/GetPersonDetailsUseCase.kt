package com.claudiogalvaodev.moviemanager.ui.usecases

import com.claudiogalvaodev.moviemanager.model.Employe
import com.claudiogalvaodev.moviemanager.repository.MoviesRepository

class GetPersonDetailsUseCase(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(personId: String): Result<Employe?> {
        return repository.getPersonDetails(personId)
    }

}