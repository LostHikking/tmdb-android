package ru.omsu.themoviedb.metadata.tmdb

data class Credits(
        val cast: List<Actor?>? = null,
        val crew: List<Crew?>? = null,
        val id: Int? = null
)