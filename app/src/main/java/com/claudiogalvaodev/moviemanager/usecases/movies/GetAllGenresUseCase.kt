package com.claudiogalvaodev.moviemanager.usecases.movies

import com.claudiogalvaodev.moviemanager.data.repository.IMoviesRepository
import com.claudiogalvaodev.moviemanager.ui.model.GenreModel

class GetAllGenresUseCase(
    private val repository: IMoviesRepository
) {

    suspend operator fun invoke(): Result<List<GenreModel>> {
        return repository.getAllGenres()
    }

}