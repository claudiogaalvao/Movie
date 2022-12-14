package com.claudiogalvaodev.moviemanager.data.repository

import com.claudiogalvaodev.moviemanager.ui.model.ScheduledNotificationsModel
import kotlinx.coroutines.flow.Flow

interface IScheduledNotificationsRepository {

    fun getAll(): Flow<Result<List<ScheduledNotificationsModel>>>

    suspend fun save(notification: ScheduledNotificationsModel): Result<Unit>

    suspend fun update(notification: ScheduledNotificationsModel): Result<Unit>

    suspend fun delete(notification: ScheduledNotificationsModel): Result<Unit>
}