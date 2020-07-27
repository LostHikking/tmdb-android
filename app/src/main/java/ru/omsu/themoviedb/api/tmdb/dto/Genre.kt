package ru.omsu.themoviedb.api.tmdb.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Genre(
        @PrimaryKey val id: Int,
        @ColumnInfo(name = "name") val name: String
)