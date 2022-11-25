package com.claudiogalvaodev.moviemanager.utils.broadcasts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.claudiogalvaodev.moviemanager.utils.notification.CineSeteNotificationManager
import com.claudiogalvaodev.moviemanager.utils.notification.channels.MovieReleaseNotificationChannel
import com.claudiogalvaodev.moviemanager.utils.notification.notifications.MovieReleaseNotification

class MovieReleaseBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val bundle = intent?.extras

            val movieId = bundle?.getInt(ARGS_MOVIE_ID) ?: 0
            val movieReleaseDate = bundle?.getString(ARGS_MOVIE_RELEASE) ?: ""
            val notificationTitle = bundle?.getString(ARGS_NOTIFICATION_TITLE) ?: ""
            val notificationMessage = bundle?.getString(ARGS_NOTIFICATION_MESSAGE) ?: ""

            val cineSeteNotificationManager = CineSeteNotificationManager(it)
            val movieReleaseNotificationChannel = MovieReleaseNotificationChannel()
            val notification = MovieReleaseNotification(
                id = movieId,
                title = notificationTitle,
                message = notificationMessage,
                movieId = movieId,
                movieRelease = movieReleaseDate
            )

            cineSeteNotificationManager.showNotification(movieReleaseNotificationChannel.channelId, notification)
        }

    }

    companion object {
        const val ARGS_MOVIE_ID = "movieId"
        const val ARGS_MOVIE_RELEASE = "movieRelease"

        const val ARGS_NOTIFICATION_TITLE = "notificationTitle"
        const val ARGS_NOTIFICATION_MESSAGE = "notificationMessage"
    }
}