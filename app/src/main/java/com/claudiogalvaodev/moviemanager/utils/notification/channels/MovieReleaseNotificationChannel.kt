package com.claudiogalvaodev.moviemanager.utils.notification.channels

import android.app.NotificationManager
import com.claudiogalvaodev.moviemanager.utils.notification.notifications.MovieReleaseNotification

class MovieReleaseNotificationChannel: INotificationChannel {
    override val channelId: String
        get() = "movie_release_channel"
    override val channelName: String
        get() = "Movie releases"
    override val channelDescription: String
        get() = "Used to notify when a movie that the user was interested become released!"
    override val channelImportance: Int
        get() = NotificationManager.IMPORTANCE_DEFAULT

    private val _notifications = mutableListOf<INotification>()
    override val notifications: List<INotification>
        get() = _notifications

    override fun addNotification(notification: INotification) {
        _notifications.add(notification)
    }

    override fun cleanNotifications() {
        _notifications.clear()
    }
}