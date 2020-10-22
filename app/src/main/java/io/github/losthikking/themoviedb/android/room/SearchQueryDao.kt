package io.github.losthikking.themoviedb.android.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.github.losthikking.themoviedb.android.data.SearchQuery

@Dao
interface SearchQueryDao {
    @Query("SELECT * FROM searchquery")
    suspend fun getAll(): List<SearchQuery>

    @Query("SELECT * FROM searchquery WHERE `query` LIKE :query")
    suspend fun findByQuery(query: String): SearchQuery

    @Insert
    suspend fun insertAll(vararg searchQueries: SearchQuery)

    @Delete
    suspend fun delete(searchQuery: SearchQuery)
}