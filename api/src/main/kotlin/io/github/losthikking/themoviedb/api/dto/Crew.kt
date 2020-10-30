package io.github.losthikking.themoviedb.api.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Crew(
    @SerialName("credit_id")
    val creditId: String?,
    val department: String?,
    val gender: Int?,
    val id: Int,
    val job: String?,
    val name: String?,
    @SerialName("profile_path")
    val profilePath: String?
)
