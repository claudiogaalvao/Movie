package com.claudiogalvaodev.moviemanager.ui.usecases

import com.claudiogalvaodev.moviemanager.data.repository.MoviesRepository

class GetAllMyListsUseCase(
    private val repository: MoviesRepository
) {

    fun invoke() = repository.getAllMyLists()

}