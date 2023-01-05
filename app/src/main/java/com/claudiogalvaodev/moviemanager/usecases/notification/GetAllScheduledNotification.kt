package com.claudiogalvaodev.moviemanager.usecases.notification

import com.claudiogalvaodev.moviemanager.data.repository.IScheduledNotificationsRepository

class GetAllScheduledNotification(
    private val notificationsRepository: IScheduledNotificationsRepository
) {

    operator fun invoke() = notificationsRepository.getAll()

}