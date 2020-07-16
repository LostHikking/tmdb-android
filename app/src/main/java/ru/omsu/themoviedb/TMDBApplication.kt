package ru.omsu.themoviedb

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TMDBApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TMDBApplication)
            modules(tmdbModule)
        }
    }
}