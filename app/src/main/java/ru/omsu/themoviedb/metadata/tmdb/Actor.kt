package ru.omsu.themoviedb.metadata.tmdb

data class Actor(
        val cast_id: Int,
        val character: String?,
        val credit_id: String?,
        val gender: Int?,
        val id: Int?,
        val name: String?,
        val order: Int?,
        val profile_path: String?
)