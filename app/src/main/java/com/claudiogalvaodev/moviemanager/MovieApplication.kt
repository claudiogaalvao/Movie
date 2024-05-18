package com.claudiogalvaodev.moviemanager

import android.app.Application
import com.claudiogalvaodev.moviemanager.di.appModules
import com.claudiogalvaodev.moviemanager.utils.notification.ICineSeteNotificationManager
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieApplication: Application() {

    private val notificationManager: ICineSeteNotificationManager by inject()

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MovieApplication)
            modules(appModules)
            // TODO Setup POST_NOTIFICATION permission
            // createNotificationChannels()
        }
    }

    private fun createNotificationChannels() {
        notificationManager.createNotificationChannels()
    }

}