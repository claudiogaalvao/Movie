package com.claudiogalvaodev.moviemanager.data.repository

import com.claudiogalvaodev.moviemanager.data.bd.datasource.ICustomListsLocalDatasource

class CustomListsRepository(
    private val customListsLocalRepository: ICustomListsLocalDatasource,
): ICustomListsRepository {

    override suspend fun saveMovieOnCustomList(
        listId: Int,
        movieId: Int,
        posterPath: String
    ): Result<Unit> {
        return customListsLocalRepository.saveMovieOnCustomList(listId, movieId, posterPath)
    }

    override suspend fun removeMoveFromCustomList(movieId: Int, listId: Int) =
        customListsLocalRepository.removeMoveFromCustomList(movieId, listId)

    override suspend fun getMoviesByListId(listId: Int) = customListsLocalRepository.getMoviesByListId(listId)

    override suspend fun getAllCustomList() = customListsLocalRepository.getAllCustomList()

    override suspend fun createNewCustomList(listName: String) =
        customListsLocalRepository.createNewCustomList(listName)

    override suspend fun deleteCustomList(listId: Int) = customListsLocalRepository.deleteCustomList(listId)


}