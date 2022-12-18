package com.claudiogalvaodev.moviemanager.data.bd.datasource

import com.claudiogalvaodev.moviemanager.ui.model.ScheduledNotificationsModel
import kotlinx.coroutines.flow.Flow

interface IScheduledNotificationsLocalDatasource {

    fun getAll(): Flow<Result<List<ScheduledNotificationsModel>>>

    suspend fun getScheduledNotificationByMovieId(movieId: Int): Result<ScheduledNotificationsModel>

    suspend fun save(notification: ScheduledNotificationsModel): Result<Unit>

    suspend fun update(notification: ScheduledNotificationsModel): Result<Unit>

    suspend fun delete(notification: ScheduledNotificationsModel): Result<Unit>
}