package io.github.losthikking.themoviedb.api.dto

data class Credits(
        val id: Int,
        val cast: List<Actor>,
        val crew: List<Crew>
)