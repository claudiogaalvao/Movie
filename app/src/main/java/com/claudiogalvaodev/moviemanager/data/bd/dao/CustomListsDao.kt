package com.claudiogalvaodev.moviemanager.data.bd.dao

import androidx.room.*
import com.claudiogalvaodev.moviemanager.data.bd.entity.CustomListEntity

@Dao
interface CustomListsDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun create(listEntity: CustomListEntity): Long

    @Query("SELECT * FROM CustomListEntity")
    suspend fun getAll(): List<CustomListEntity>

    @Query("SELECT * FROM CustomListEntity WHERE id = :listId")
    suspend fun getCustomListById(listId: Int): CustomListEntity

    @Query("DELETE FROM CustomListEntity WHERE id = :listId")
    suspend fun delete(listId: Int)

    @Query("UPDATE CustomListEntity SET posterPath = :newPosterPath WHERE id = :listId")
    suspend fun updatePosterPath(listId: Int, newPosterPath: String)

}