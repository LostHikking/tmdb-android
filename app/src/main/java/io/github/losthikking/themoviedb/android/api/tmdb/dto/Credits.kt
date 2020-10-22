package io.github.losthikking.themoviedb.android.api.tmdb.dto

data class Credits(
        val id: Int,
        val cast: List<Actor>,
        val crew: List<Crew>
)