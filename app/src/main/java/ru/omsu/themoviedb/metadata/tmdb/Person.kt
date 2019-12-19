package ru.omsu.themoviedb.metadata.tmdb

data class Person(
        val adult: Boolean? = null,
        val also_known_as: List<String?>? = null,
        val biography: String? = null,
        val birthday: String? = null,
        val deathday: Any? = null,
        val gender: Int? = null,
        val homepage: Any? = null,
        val id: Int? = null,
        val imdb_id: String? = null,
        val known_for_department: String? = null,
        val name: String? = null,
        val place_of_birth: String? = null,
        val popularity: Double? = null,
        val profile_path: String? = null
)