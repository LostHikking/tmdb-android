package io.github.losthikking.themoviedb.api.dto.movie

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Collection(
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    val id: Int,
    val name: String,
    val overview: String? = null,
    val parts: List<Movie> = listOf(),
    @SerialName("poster_path")
    val posterPath: String?
)
