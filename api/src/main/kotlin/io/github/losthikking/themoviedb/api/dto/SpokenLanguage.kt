package io.github.losthikking.themoviedb.api.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpokenLanguage(
    @SerialName("iso_639_1")
    val iso6391: String,
    val name: String
)
