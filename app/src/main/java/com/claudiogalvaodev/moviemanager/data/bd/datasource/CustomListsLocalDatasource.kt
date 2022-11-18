package com.claudiogalvaodev.moviemanager.data.bd.datasource

import com.claudiogalvaodev.moviemanager.data.bd.dao.CustomListsDao
import com.claudiogalvaodev.moviemanager.data.bd.dao.MoviesSavedDao
import com.claudiogalvaodev.moviemanager.data.bd.entity.*
import com.claudiogalvaodev.moviemanager.ui.model.CustomListModel
import com.claudiogalvaodev.moviemanager.ui.model.MovieModel
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class CustomListsLocalDatasource(
    private val customListsDao: CustomListsDao,
    private val moviesSavedDao: MoviesSavedDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ICustomListsLocalDatasource {

    override suspend fun saveMovieOnCustomList(listId: Int, movieId: Int, posterPath: String): Result<Unit> {
        return try {
            moviesSavedDao.saveMovie(MovieSavedEntity(movieId = movieId, moviePosterUrl = posterPath, myListId = listId))
            customListsDao.updatePosterPath(listId, posterPath)
            Result.success(Unit)
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().log(e.message.toString())
            Result.failure(exception = Exception("Something went wrong when try to save movie to list"))
        }
    }

    override suspend fun removeMoveFromCustomList(movieId: Int, listId: Int): Result<Unit> {
        return try {
            moviesSavedDao.remove(movieId, listId)
            // TODO Update poster path on Custom List
            Result.success(Unit)
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().log(e.message.toString())
            Result.failure(exception = Exception("Something went wrong when try to delete custom list"))
        }
    }

    override suspend fun getMoviesByListId(listId: Int) : Result<List<MovieModel>> {
        return try {
            val moviesSaved = moviesSavedDao.getMoviesByListId(listId)
            Result.success(moviesSaved.toListOfMovieModel())
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().log(e.message.toString())
            Result.failure(exception = Exception("Something went wrong when try to get movies by list id"))
        }
    }

    override fun getAllCustomList() : Flow<Result<List<CustomListModel>>> = flow {
        try {
            customListsDao.getAll().collect { customListsEntity ->
                val customListsModel = customListsEntity.toListOfCustomListModel()
                emit(Result.success(customListsModel))
                moviesSavedDao.getAll().collect { moviesSavedEntity ->
                    val customListsWithMovies = customListsModel.map { customListModel ->
                        customListModel.copy(movies = moviesSavedEntity
                            .filterMoviesByListId(customListModel.id)
                            .toListOfMovieModel()
                        )
                    }
                    emit(Result.success(customListsWithMovies))
                }
            }
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().log(e.message.toString())
            emit(Result.failure(exception = Exception("Something went wrong when try to get all custom list")))
        }
    }.flowOn(dispatcher)

    override suspend fun createNewCustomList(listName: String): Result<Int> {
        return try {
            val newCustomList = CustomListEntity(id = 0, name = listName, posterPath = "")
            val idGenerated = customListsDao.create(newCustomList)
            Result.success(idGenerated.toInt())
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().log(e.message.toString())
            Result.failure(exception = Exception("Something went wrong when try to create a new custom list"))
        }
    }

    override suspend fun deleteCustomList(listId: Int): Result<Unit> {
        return try {
            customListsDao.delete(listId)
            Result.success(Unit)
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().log(e.message.toString())
            Result.failure(exception = Exception("Something went wrong when try to delete custom list"))
        }
    }

}