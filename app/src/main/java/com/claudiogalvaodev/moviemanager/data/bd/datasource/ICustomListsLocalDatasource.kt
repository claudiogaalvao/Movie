package com.claudiogalvaodev.moviemanager.data.bd.datasource

import com.claudiogalvaodev.moviemanager.ui.model.CustomListModel
import com.claudiogalvaodev.moviemanager.ui.model.MovieModel

interface ICustomListsLocalDatasource {

    suspend fun saveMovieOnCustomList(listId: Int, movieId: Int, posterPath: String): Result<Unit>

    suspend fun removeMoveFromCustomList(movieId: Int, listId: Int): Result<Unit>

    suspend fun getMoviesByListId(listId: Int): Result<List<MovieModel>>

    suspend fun getAllCustomList(): Result<List<CustomListModel>>

    suspend fun createNewCustomList(listName: String): Result<Int>

    suspend fun deleteCustomList(listId: Int): Result<Unit>
}