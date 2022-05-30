package com.claudiogalvaodev.moviemanager.data.webclient.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.claudiogalvaodev.moviemanager.R
import com.claudiogalvaodev.moviemanager.ui.MainActivity
import com.claudiogalvaodev.moviemanager.ui.moviedetails.MovieDetailsActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

private const val CHANNEL_ID = "my_channel"

class FirebaseService : FirebaseMessagingService() {

    private lateinit var notificationTitle: String
    private lateinit var notificationMessage: String
    private lateinit var activityClassName: String
    private lateinit var movieId: String
    private lateinit var clickAction: String

    private val notificationManager: NotificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    @SuppressLint("UnspecifiedImmutableFlag", "ObsoleteSdkInt")
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        getRemoteData(remoteMessage)
        sendNotification()

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNoficationChannel(notificationManager)
        }
    }

    private fun getRemoteData(remoteMessage: RemoteMessage) {
        notificationTitle = remoteMessage.data["title"] ?: ""
        notificationMessage = remoteMessage.data["message"] ?: ""
        activityClassName = remoteMessage.data["activityClassName"] ?: ""
        movieId = remoteMessage.data["movieId"] ?: ""
        clickAction = remoteMessage.notification?.clickAction ?: ""
    }

    private fun getIntent(activityClassName: String?, movieId: String?): Intent {
        return when(activityClassName) {
            "MovieDetailsActivity" -> getIntentForMovieDetailsActivity(movieId)
            else -> Intent(this, MainActivity::class.java)
        }
    }

    private fun getIntentForMovieDetailsActivity(movieId: String?): Intent {
        return movieId?.let {
            MovieDetailsActivity.newInstance(this, it.toInt(), "")
        } ?: Intent(this, MainActivity::class.java)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun sendNotification() {
        val intent = getIntent(activityClassName, movieId).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            action = clickAction
        }

        val notificationID = Random.nextInt()
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, FLAG_ONE_SHOT)
        val notification = buildNotification(pendingIntent)
        notificationManager.notify(notificationID, notification)
    }

    private fun buildNotification(pendingIntent: PendingIntent?): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentText(notificationTitle)
            .setContentText(notificationMessage)
            .setSmallIcon(R.drawable.logo)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
            .setContentIntent(pendingIntent)
            .build()
    }

    private fun createNoficationChannel(notificationManager: NotificationManager) {
        val channelName = "channelName"
        val channel = NotificationChannel(CHANNEL_ID, channelName, IMPORTANCE_HIGH).apply {
            description = "My channel description"
            enableLights(true)
            lightColor = Color.BLUE
        }
        notificationManager.createNotificationChannel(channel)
    }
}