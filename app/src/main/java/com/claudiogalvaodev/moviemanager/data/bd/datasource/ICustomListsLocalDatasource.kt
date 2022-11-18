package com.claudiogalvaodev.moviemanager.data.bd.datasource

import com.claudiogalvaodev.moviemanager.ui.model.CustomListModel
import com.claudiogalvaodev.moviemanager.ui.model.MovieModel
import kotlinx.coroutines.flow.Flow

interface ICustomListsLocalDatasource {

    suspend fun saveMovieOnCustomList(listId: Int, movieId: Int, posterPath: String): Result<Unit>

    suspend fun removeMoveFromCustomList(movieId: Int, listId: Int, newPosterPath: String?): Result<Unit>

    fun getMoviesByListId(listId: Int): Flow<Result<List<MovieModel>>>

    fun getAllCustomList(): Flow<Result<List<CustomListModel>>>

    suspend fun createNewCustomList(listName: String): Result<Int>

    suspend fun deleteCustomList(listId: Int): Result<Unit>
}