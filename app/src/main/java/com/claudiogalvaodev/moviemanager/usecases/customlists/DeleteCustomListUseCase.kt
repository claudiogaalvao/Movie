package com.claudiogalvaodev.moviemanager.usecases.customlists

import com.claudiogalvaodev.moviemanager.data.repository.ICustomListsRepository

class DeleteCustomListUseCase(
    private val repository: ICustomListsRepository
) {

    suspend operator fun invoke(listId: Int) = repository.deleteCustomList(listId)

}