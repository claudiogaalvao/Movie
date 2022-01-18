package com.claudiogalvaodev.moviemanager

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.claudiogalvaodev.moviemanager.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MovieApplication)
            modules(appModules)
        }
    }
}