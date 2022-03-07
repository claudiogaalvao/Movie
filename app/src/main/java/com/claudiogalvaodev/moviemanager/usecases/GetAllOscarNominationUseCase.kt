package com.claudiogalvaodev.moviemanager.usecases

import com.claudiogalvaodev.moviemanager.data.repository.MoviesRepository

class GetAllOscarNominationUseCase(
    private val repository: MoviesRepository
) {

    suspend fun invoke() = repository.getAllOscarNomination()

}