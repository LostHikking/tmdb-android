package io.github.losthikking.themoviedb.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.github.losthikking.themoviedb.api.tmdb.dto.Genre
import io.github.losthikking.themoviedb.data.SearchQuery

@Database(entities = [Genre::class, SearchQuery::class], version = 1)
@TypeConverters(Converters::class)
abstract class TMDBDatabase : RoomDatabase() {
    abstract fun genreDao(): GenreDao
    abstract fun searchQueryDao(): SearchQueryDao
}