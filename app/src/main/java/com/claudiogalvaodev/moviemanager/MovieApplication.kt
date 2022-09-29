package com.claudiogalvaodev.moviemanager

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import com.claudiogalvaodev.moviemanager.data.bd.CineSeteDatabase
import com.claudiogalvaodev.moviemanager.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MovieApplication)
            modules(appModules)
            createNotificationChannel()
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val name = getString(R.string.channel_name)
        val descriptionText = getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        const val CHANNEL_ID = "first_channel"
    }
}