package com.claudiogalvaodev.moviemanager.data.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.claudiogalvaodev.moviemanager.data.bd.entity.MovieSaved
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesSavedDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun saveMovie(movieToSave: MovieSaved)

    @Query("SELECT * FROM MovieSaved WHERE myListId = :myListId")
    fun getMoviesByMyListId(myListId: Int): Flow<List<MovieSaved>>

}