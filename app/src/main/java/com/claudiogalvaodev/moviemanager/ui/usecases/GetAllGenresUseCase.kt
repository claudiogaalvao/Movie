package com.claudiogalvaodev.moviemanager.ui.usecases

import com.claudiogalvaodev.moviemanager.data.model.Genre
import com.claudiogalvaodev.moviemanager.data.repository.MoviesRepository

class GetAllGenresUseCase(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(): Result<List<Genre>> {
        return repository.getAllGenres()
    }

}