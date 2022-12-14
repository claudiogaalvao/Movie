package com.claudiogalvaodev.moviemanager.data.repository

import com.claudiogalvaodev.moviemanager.data.bd.datasource.IScheduledNotificationsLocalDatasource
import com.claudiogalvaodev.moviemanager.ui.model.ScheduledNotificationsModel

class ScheduledNotificationsRepository(
    private val localDatasource: IScheduledNotificationsLocalDatasource
): IScheduledNotificationsRepository {

    override fun getAll() = localDatasource.getAll()

    override suspend fun save(notification: ScheduledNotificationsModel): Result<Unit> =
        localDatasource.save(notification)

    override suspend fun update(notification: ScheduledNotificationsModel): Result<Unit> =
        localDatasource.update(notification)

    override suspend fun delete(notification: ScheduledNotificationsModel): Result<Unit> =
        localDatasource.delete(notification)

}