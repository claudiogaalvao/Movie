package com.claudiogalvaodev.moviemanager.data.bd.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.claudiogalvaodev.moviemanager.data.bd.entity.ScheduledNotificationsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduledNotificationsDao {

    @Query("SELECT * FROM ScheduledNotificationsEntity")
    fun getAll(): Flow<List<ScheduledNotificationsEntity>>

    @Query("SELECT * FROM ScheduledNotificationsEntity where movieId = :movieId")
    suspend fun getScheduledNotificationByMovieId(movieId: Int): ScheduledNotificationsEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(scheduledNotificationsEntity: ScheduledNotificationsEntity)

    @Update
    suspend fun update(scheduledNotificationsEntity: ScheduledNotificationsEntity)

    @Delete
    suspend fun delete(scheduledNotificationsEntity: ScheduledNotificationsEntity)

}