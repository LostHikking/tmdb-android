package io.github.losthikking.themoviedb.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class Credits(
        val id: Int,
        val cast: List<Actor>,
        val crew: List<Crew>
)