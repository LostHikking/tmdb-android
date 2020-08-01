package io.github.losthikking.themoviedb.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.github.losthikking.themoviedb.api.tmdb.dto.Genre

@Dao
interface GenreDao {
    @Query("SELECT * FROM genre")
    suspend fun getAll(): List<Genre>

    @Query("SELECT * FROM genre WHERE id IN (:genreIds)")
    suspend fun loadAllByIds(genreIds: IntArray): List<Genre>

    @Query("SELECT * FROM genre WHERE name LIKE :name LIMIT 1")
    suspend fun findByName(name: String): Genre

    @Insert
    suspend fun insertAll(genres: List<Genre>)

    @Delete
    suspend fun delete(genre: Genre)
}