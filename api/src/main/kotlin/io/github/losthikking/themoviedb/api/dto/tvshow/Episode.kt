package io.github.losthikking.themoviedb.api.dto.tvshow

import io.github.losthikking.themoviedb.api.dto.Actor
import io.github.losthikking.themoviedb.api.dto.Crew
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Episode(
    @SerialName("air_date")
    val air_date: String?,
    val crew: List<Crew>,
    val episode_number: Int? = null,
    @SerialName("guest_stars")
    val guest_stars: List<Actor>,
    val id: Int,
    val name: String?,
    val overview: String?,
    @SerialName("production_code")
    val productionCode: String?,
    @SerialName("season_number")
    val seasonNumber: Int?,
    @SerialName("show_id")
    val showId: Int?,
    @SerialName("still_path")
    val stillPath: String?,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int
)
