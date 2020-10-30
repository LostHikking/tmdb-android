package io.github.losthikking.themoviedb.api.dto.movie

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Collection(
    @SerialName("backdrop_path")
    val backdropPath: String?,
    val id: Int,
    val name: String?,
    val overview: String?,
    val parts: List<Movie>,
    @SerialName("poster_path")
    val posterPath: String?
)
