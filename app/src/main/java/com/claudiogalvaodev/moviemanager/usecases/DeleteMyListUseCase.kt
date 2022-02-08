package com.claudiogalvaodev.moviemanager.usecases

import com.claudiogalvaodev.moviemanager.data.repository.MoviesRepository

class DeleteMyListUseCase(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(myListId: Int) {
        return repository.deleteMyList(myListId)
    }

}