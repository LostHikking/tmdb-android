package io.github.losthikking.themoviedb.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    val id: Int,
    val name: String
)
