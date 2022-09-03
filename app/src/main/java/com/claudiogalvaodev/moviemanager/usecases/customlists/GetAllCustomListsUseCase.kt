package com.claudiogalvaodev.moviemanager.usecases.customlists

import com.claudiogalvaodev.moviemanager.data.repository.ICustomListsRepository

class GetAllCustomListsUseCase(
    private val repository: ICustomListsRepository
) {

    suspend fun invoke() = repository.getAllCustomList()

}