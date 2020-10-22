package io.github.losthikking.themoviedb.api.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Video(
        val id: String,
        @SerialName("iso_3166_1")
        val iso31661: String,
        @SerialName("iso_639_1")
        val iso6391: String,
        val key: String,
        val name: String,
        val site: String,
        val size: Int,
        val type: String
)