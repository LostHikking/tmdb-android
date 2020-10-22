package io.github.losthikking.themoviedb.api.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Person(
        val adult: Boolean,
        @SerialName("also_known_as")
        val alsoKnownAs: List<String>,
        val biography: String?,
        val birthday: String?,
        val deathday: String?,
        val gender: Int?,
        val homepage: String?,
        val id: Int?,
        @SerialName("imdb_id")
        val imdbId: String?,
        @SerialName("known_for_department")
        val knownForDepartment: String?,
        val name: String?,
        @SerialName("place_of_birth")
        val placeOfBirth: String?,
        val popularity: Double?,
        @SerialName("profile_path")
        val profilePath: String?
)