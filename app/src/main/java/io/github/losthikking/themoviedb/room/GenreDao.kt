package io.github.losthikking.themoviedb.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.github.losthikking.themoviedb.api.tmdb.dto.Genre

@Dao
interface GenreDao {
    @Query("SELECT * FROM genre")
    fun getAll(): List<Genre>

    @Query("SELECT * FROM genre WHERE id IN (:genreIds)")
    fun loadAllByIds(genreIds: IntArray): List<Genre>

    @Query("SELECT * FROM genre WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): Genre

    @Insert
    fun insertAll(vararg users: Genre)

    @Delete
    fun delete(genre: Genre)
}