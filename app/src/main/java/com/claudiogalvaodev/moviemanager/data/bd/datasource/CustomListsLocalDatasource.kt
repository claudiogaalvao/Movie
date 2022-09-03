package com.claudiogalvaodev.moviemanager.data.bd.datasource

import com.claudiogalvaodev.moviemanager.data.bd.dao.CustomListsDao
import com.claudiogalvaodev.moviemanager.data.bd.dao.MoviesSavedDao
import com.claudiogalvaodev.moviemanager.data.bd.entity.CustomListEntity
import com.claudiogalvaodev.moviemanager.data.bd.entity.MovieSavedEntity
import com.claudiogalvaodev.moviemanager.data.bd.entity.toListOfCustomListModel
import com.claudiogalvaodev.moviemanager.data.bd.entity.toListOfMovieModel
import com.claudiogalvaodev.moviemanager.ui.model.CustomListModel
import com.claudiogalvaodev.moviemanager.ui.model.MovieModel

class CustomListsLocalDatasource(
    private val customListsDao: CustomListsDao,
    private val moviesSavedDao: MoviesSavedDao,
): ICustomListsLocalDatasource {

    private suspend fun getAllMoviesSaved() : Result<List<MovieSavedEntity>> {
        return try {
            val moviesSaved = moviesSavedDao.getAll()
            Result.success(moviesSaved)
        } catch (e: Exception) {
            Result.failure(exception = Exception("Something went wrong when try to get all movies saved"))
        }
    }

    override suspend fun saveMovieOnCustomList(listId: Int, movieId: Int, posterPath: String): Result<Unit> {
        return try {
            moviesSavedDao.saveMovie(MovieSavedEntity(id = 1, movieId = movieId, posterPath = posterPath, listId = listId))
            customListsDao.updatePosterPath(listId, posterPath)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(exception = Exception("Something went wrong when try to save movie to list"))
        }
    }

    override suspend fun removeMoveFromCustomList(movieId: Int, listId: Int): Result<Unit> {
        return try {
            moviesSavedDao.remove(movieId, listId)
            // TODO Update poster path on Custom List
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(exception = Exception("Something went wrong when try to delete custom list"))
        }
    }

    override suspend fun getMoviesByListId(listId: Int) : Result<List<MovieModel>> {
        return try {
            val moviesSaved = moviesSavedDao.getMoviesByListId(listId)
            Result.success(moviesSaved.toListOfMovieModel())
        } catch (e: Exception) {
            Result.failure(exception = Exception("Something went wrong when try to get movies by list id"))
        }
    }

    // Custom Lists

    override suspend fun getAllCustomList() : Result<List<CustomListModel>> {
        return try {
            val customListsEntity = customListsDao.getAll()
            val moviesSaved = getAllMoviesSaved()

            val customListsModel = customListsEntity.toListOfCustomListModel()

            if (moviesSaved.isSuccess) {
                val movies = moviesSaved.getOrNull()
                movies?.let { movieSavedList ->
                    val customListsWithMovies = customListsModel.map { customListModel ->
                        customListModel.copy(movies = movieSavedList
                            .filter { it.listId == customListModel.id }
                            .toListOfMovieModel()
                        )
                    }
                    Result.success(customListsWithMovies)
                } ?: Result.success(customListsModel)
            } else Result.success(customListsModel)
        } catch (e: Exception) {
            Result.failure(exception = Exception("Something went wrong when try to get all custom list"))
        }
    }

    override suspend fun createNewCustomList(listName: String): Result<Int> {
        return try {
            val newCustomList = CustomListEntity(id = 1, name = listName, posterPath = "")
            val idGenerated = customListsDao.create(newCustomList)
            Result.success(idGenerated.toInt())
        } catch (e: Exception) {
            Result.failure(exception = Exception("Something went wrong when try to create a new custom list"))
        }
    }

    override suspend fun deleteCustomList(listId: Int): Result<Unit> {
        return try {
            customListsDao.delete(listId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(exception = Exception("Something went wrong when try to delete custom list"))
        }
    }

}