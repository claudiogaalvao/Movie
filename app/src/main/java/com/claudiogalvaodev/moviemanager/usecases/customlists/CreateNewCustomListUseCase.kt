package com.claudiogalvaodev.moviemanager.usecases.customlists

import com.claudiogalvaodev.moviemanager.data.repository.ICustomListsRepository

class CreateNewCustomListUseCase(
    private val repository: ICustomListsRepository
) {

    suspend operator fun invoke(listName: String) = repository.createNewCustomList(listName)

}