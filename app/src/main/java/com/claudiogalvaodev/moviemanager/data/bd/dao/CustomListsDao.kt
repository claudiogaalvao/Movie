package com.claudiogalvaodev.moviemanager.data.bd.dao

import androidx.room.*
import com.claudiogalvaodev.moviemanager.data.bd.entity.CustomListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomListsDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun create(listEntity: CustomListEntity): Long

    @Query("SELECT * FROM CustomListEntity")
    fun getAll(): Flow<List<CustomListEntity>>

    @Query("DELETE FROM CustomListEntity WHERE id = :listId")
    suspend fun delete(listId: Int)

    @Query("UPDATE CustomListEntity SET posterPath = :newPosterPath WHERE id = :listId")
    suspend fun updatePosterPath(listId: Int, newPosterPath: String)

}