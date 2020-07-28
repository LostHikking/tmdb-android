package io.github.losthikking.themoviedb.api.tmdb.dto

data class Credits(
        val id: Int,
        val cast: List<Actor>,
        val crew: List<Crew>
)