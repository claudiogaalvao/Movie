package com.claudiogalvaodev.moviemanager.data.bd.dao

import androidx.room.*
import com.claudiogalvaodev.moviemanager.data.bd.entity.MovieSaved
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesSavedDao {

    @Query("SELECT * FROM MovieSaved")
    fun getAll(): Flow<List<MovieSaved>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun saveMovie(movieToSave: MovieSaved)

    @Query("SELECT * FROM MovieSaved WHERE myListId = :myListId")
    fun getMoviesByMyListId(myListId: Int): Flow<List<MovieSaved>>

    @Query("DELETE FROM MovieSaved WHERE movieId = :movieId AND myListId = :myListId")
    suspend fun remove(movieId: Int, myListId: Int)
}