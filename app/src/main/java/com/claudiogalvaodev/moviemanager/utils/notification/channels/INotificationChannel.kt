package com.claudiogalvaodev.moviemanager.utils.notification.channels

interface INotificationChannel {
    val channelId: String
    val channelName: String
    val channelDescription: String
    val channelImportance: Int
    val notifications: List<INotification>

    fun addNotification(notification: INotification)
    fun cleanNotifications()
}