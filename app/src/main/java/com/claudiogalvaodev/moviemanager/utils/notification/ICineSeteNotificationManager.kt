package com.claudiogalvaodev.moviemanager.utils.notification

import com.claudiogalvaodev.moviemanager.utils.notification.channels.INotification

interface ICineSeteNotificationManager {
    fun createNotificationChannels()
    fun showNotification(
        notificationChannelId: String,
        notification: INotification
    )
    fun scheduleNotification(
        timeInMillis: Long,
        notification: INotification
    )
}