package com.claudiogalvaodev.moviemanager.data.bd.datasource

import com.claudiogalvaodev.moviemanager.data.bd.dao.ScheduledNotificationsDao
import com.claudiogalvaodev.moviemanager.data.bd.entity.toListOfScheduledNotificationModel
import com.claudiogalvaodev.moviemanager.data.bd.entity.toModel
import com.claudiogalvaodev.moviemanager.ui.model.ScheduledNotificationsModel
import com.claudiogalvaodev.moviemanager.ui.model.toEntity
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class ScheduledNotificationsLocalDatasource(
    private val scheduledNotificationsDao: ScheduledNotificationsDao
) : IScheduledNotificationsLocalDatasource {
    override fun getAll(): Flow<Result<List<ScheduledNotificationsModel>>> = flow {
        try {
            scheduledNotificationsDao.getAll().collect {
                emit(Result.success(it.toListOfScheduledNotificationModel()))
            }
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().log(e.message.toString())
            emit(Result.failure(exception = Exception("Something went wrong when try to get scheduled notifications")))
        }
    }

    override suspend fun getScheduledNotificationByMovieId(movieId: Int): Result<ScheduledNotificationsModel> {
        return try {
            val scheduledNotificationEntity = scheduledNotificationsDao.getScheduledNotificationByMovieId(movieId)
            Result.success(scheduledNotificationEntity.toModel())
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().log(e.message.toString())
            Result.failure(exception = Exception("Something went wrong when try to save scheduled notification"))
        }
    }

    override suspend fun save(notification: ScheduledNotificationsModel): Result<Unit> {
        return try {
            scheduledNotificationsDao.save(notification.toEntity())
            Result.success(Unit)
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().log(e.message.toString())
            Result.failure(exception = Exception("Something went wrong when try to save scheduled notification"))
        }
    }

    override suspend fun update(notification: ScheduledNotificationsModel): Result<Unit> {
        return try {
            scheduledNotificationsDao.update(notification.toEntity())
            Result.success(Unit)
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().log(e.message.toString())
            Result.failure(exception = Exception("Something went wrong when try to update scheduled notification"))
        }
    }

    override suspend fun delete(notification: ScheduledNotificationsModel): Result<Unit> {
        return try {
            scheduledNotificationsDao.delete(notification.toEntity())
            Result.success(Unit)
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().log(e.message.toString())
            Result.failure(exception = Exception("Something went wrong when try to delete scheduled notification"))
        }
    }
}