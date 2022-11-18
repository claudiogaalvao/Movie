package com.claudiogalvaodev.moviemanager.data.bd.dao

import androidx.room.*
import com.claudiogalvaodev.moviemanager.data.bd.entity.MovieSavedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesSavedDao {

    @Query("SELECT * FROM MovieSavedEntity")
    fun getAll(): Flow<List<MovieSavedEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovie(movieToSave: MovieSavedEntity)

    @Query("SELECT * FROM MovieSavedEntity WHERE myListId = :listId")
    suspend fun getMoviesByListId(listId: Int): List<MovieSavedEntity>

    @Query("DELETE FROM MovieSavedEntity WHERE movieId = :movieId AND myListId = :listId")
    suspend fun remove(movieId: Int, listId: Int)
}