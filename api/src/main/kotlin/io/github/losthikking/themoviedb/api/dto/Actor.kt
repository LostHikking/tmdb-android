package io.github.losthikking.themoviedb.api.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Actor(
        @SerialName("cast_id")
        val castId: Int,
        val character: String?,
        @SerialName("credit_id")
        val creditId: String?,
        val gender: Int?,
        val id: Int?,
        val name: String?,
        val order: Int?,
        @SerialName("profile_path")
        val profilePath: String?
)