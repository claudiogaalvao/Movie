package com.claudiogalvaodev.moviemanager.usecases.notification

import com.claudiogalvaodev.moviemanager.data.repository.IScheduledNotificationsRepository

class HasMovieReleaseScheduledNotification(
    private val notificationsRepository: IScheduledNotificationsRepository
) {
    suspend operator fun invoke(
        movieId: Int
    ): Boolean {
        val notificationResult = notificationsRepository
            .getScheduledNotificationByMovieId(movieId)
        return if (notificationResult.isSuccess) {
            notificationResult.getOrNull()?.let {
                true
            } ?: false
        } else false
    }
}