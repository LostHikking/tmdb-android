package ru.omsu.themoviedb.metadata.tmdb

data class Credits(
        val id: Int,
        val cast: List<Actor>,
        val crew: List<Crew>
)