package io.github.losthikking.themoviedb.room

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.losthikking.themoviedb.api.tmdb.dto.Genre

@Database(entities = [Genre::class], version = 1)
abstract class TMDBDatabase : RoomDatabase() {
    abstract fun genreDao(): GenreDao
}