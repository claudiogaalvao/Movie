package com.claudiogalvaodev.moviemanager.utils.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.claudiogalvaodev.moviemanager.R
import com.claudiogalvaodev.moviemanager.utils.broadcasts.MovieReleaseBroadcastReceiver
import com.claudiogalvaodev.moviemanager.utils.notification.channels.INotification
import com.claudiogalvaodev.moviemanager.utils.notification.channels.INotificationChannel
import com.claudiogalvaodev.moviemanager.utils.notification.channels.MovieReleaseNotificationChannel

class CineSeteNotificationManager(
    private val context: Context
): ICineSeteNotificationManager {

    private val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    private val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager

    private val channels: List<INotificationChannel> = listOf(
        MovieReleaseNotificationChannel()
    )

    override fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            for (channel in channels) {
                val notificationChannel = NotificationChannel(
                    channel.channelId,
                    channel.channelName,
                    channel.channelImportance
                ).apply {
                    description = channel.channelDescription
                }
                notificationManager.createNotificationChannel(notificationChannel)
            }
        }
    }

    override fun showNotification(
        notificationChannelId: String,
        notification: INotification
    ) {
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            999,
            notification.getDestination(context),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notificationBuild = NotificationCompat.Builder(context, notificationChannelId)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle(notification.title)
            .setContentText(notification.message)
            .setContentIntent(activityPendingIntent)
            .build()

        notificationManager.notify(notification.id, notificationBuild)
    }

    override fun scheduleNotification(
        timeInMillis: Long,
        notification: INotification
    ) {
        val broadcastIntent = Intent(context, MovieReleaseBroadcastReceiver::class.java).apply {
            putExtras(notification.getBundle())
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notification.id,
            broadcastIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        // save on room
        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            timeInMillis,
            pendingIntent
        )
    }

    // cancel alarm

    // update alarm

    // get alarms

}