package com.claudiogalvaodev.moviemanager.usecases.movies

import com.claudiogalvaodev.moviemanager.data.repository.IMoviesRepository
import com.claudiogalvaodev.moviemanager.ui.model.PersonModel

class GetPersonDetailsUseCase(
    private val repository: IMoviesRepository
) {

    suspend operator fun invoke(personId: Int): Result<PersonModel?> {
        return repository.getPersonDetails(personId)
    }

}