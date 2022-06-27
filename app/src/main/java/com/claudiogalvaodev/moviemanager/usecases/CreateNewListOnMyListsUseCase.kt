package com.claudiogalvaodev.moviemanager.usecases

import com.claudiogalvaodev.moviemanager.data.bd.entity.UserListEntity
import com.claudiogalvaodev.moviemanager.data.repository.MoviesRepository

class CreateNewListOnMyListsUseCase(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(newListEntity: UserListEntity) = repository.createNewList(newListEntity)

}