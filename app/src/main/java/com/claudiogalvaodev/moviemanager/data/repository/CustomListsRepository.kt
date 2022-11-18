package com.claudiogalvaodev.moviemanager.data.repository

import com.claudiogalvaodev.moviemanager.data.bd.datasource.ICustomListsLocalDatasource

class CustomListsRepository(
    private val customListsLocalDatasource: ICustomListsLocalDatasource,
): ICustomListsRepository {

    override suspend fun saveMovieOnCustomList(
        listId: Int,
        movieId: Int,
        posterPath: String
    ): Result<Unit> {
        return customListsLocalDatasource.saveMovieOnCustomList(listId, movieId, posterPath)
    }

    override suspend fun removeMoveFromCustomList(movieId: Int, listId: Int) =
        customListsLocalDatasource.removeMoveFromCustomList(movieId, listId)

    override suspend fun getMoviesByListId(listId: Int) = customListsLocalDatasource.getMoviesByListId(listId)

    override fun getAllCustomList() = customListsLocalDatasource.getAllCustomList()

    override suspend fun createNewCustomList(listName: String) =
        customListsLocalDatasource.createNewCustomList(listName)

    override suspend fun deleteCustomList(listId: Int) = customListsLocalDatasource.deleteCustomList(listId)


}