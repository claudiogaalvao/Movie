package com.claudiogalvaodev.moviemanager.usecases

import com.claudiogalvaodev.moviemanager.data.repository.MoviesRepository

class GetAllUserListsUseCase(
    private val repository: MoviesRepository
) {

    fun invoke() = repository.getAllMyLists()

}