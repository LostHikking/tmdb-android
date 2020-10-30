package io.github.losthikking.themoviedb.android

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.github.losthikking.themoviedb.api.Service
import java.util.Locale
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideTmdbService(): Service {
        return Service(API_KEY, Locale.getDefault().toLanguageTag(), Locale.getDefault().country)
    }
}
