package io.github.losthikking.themoviedb.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class Videos(
    val id: Int,
    val results: List<Video>
)
