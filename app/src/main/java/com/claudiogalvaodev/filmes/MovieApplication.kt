package com.claudiogalvaodev.filmes

import android.app.Application
import com.claudiogalvaodev.filmes.di.appModules
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