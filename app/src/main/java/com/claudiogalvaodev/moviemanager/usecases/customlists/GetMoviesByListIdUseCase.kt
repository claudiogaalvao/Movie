package com.claudiogalvaodev.moviemanager.usecases.customlists

import com.claudiogalvaodev.moviemanager.data.repository.ICustomListsRepository

class GetMoviesByListIdUseCase(
    private val repository: ICustomListsRepository
) {

    operator fun invoke(listId: Int) = repository.getMoviesByListId(listId)

}