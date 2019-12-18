package ru.omsu.themoviedb.metadata.tmdb

data class Person(
        val adult: Boolean? = null,
        val gender: Int? = null,
        val id: Int? = null,
        val item: List<Item?>? = null,
        val known_for_department: String? = null,
        val name: String? = null,
        val popularity: Double? = null,
        val profile_path: String? = null
)