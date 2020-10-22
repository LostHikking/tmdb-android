package io.github.losthikking.themoviedb.api.dto.tvshow

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Season(
        @SerialName("_id")
        val _id: String?,
        @SerialName("air_date")
        val airDate: String?,
        val episodes: List<Episode>,
        @SerialName("episode_count")
        val episodeCount: Int?,
        val id: Int?,
        val name: String?,
        val overview: String?,
        @SerialName("poster_path")
        val posterPath: String?,
        @SerialName("season_number")
        val seasonNumber: Int?,
        @SerialName("show_id")
        val showId: Int?
)