package com.claudiogalvaodev.moviemanager.usecases

import com.claudiogalvaodev.moviemanager.data.bd.entity.MyList
import com.claudiogalvaodev.moviemanager.data.repository.MoviesRepository

class CreateNewListOnMyListsUseCase(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(newList: MyList) = repository.createNewList(newList)

}