package io.github.losthikking.themoviedb.android.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.github.losthikking.themoviedb.android.data.SearchQuery

@Database(entities = [SearchQuery::class], version = 1)
@TypeConverters(Converters::class)
abstract class TMDBDatabase : RoomDatabase() {
    abstract fun searchQueryDao(): SearchQueryDao
}
