package com.claudiogalvaodev.moviemanager.data.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.claudiogalvaodev.moviemanager.data.bd.entity.OscarNomination

@Dao
interface OscarNominationsDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun populate(oscarNominactions: List<OscarNomination>)

    @Query("SELECT * FROM OscarNomination")
    suspend fun getAll(): List<OscarNomination>
}