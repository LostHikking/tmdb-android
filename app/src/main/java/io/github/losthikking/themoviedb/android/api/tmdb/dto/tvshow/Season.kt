package io.github.losthikking.themoviedb.android.api.tmdb.dto.tvshow

import com.google.gson.annotations.SerializedName


data class Season(
        @SerializedName("_id")
        val _id: String?,
        @SerializedName("air_date")
        val airDate: String?,
        val episodes: List<Episode>,
        @SerializedName("episode_count")
        val episodeCount: Int?,
        val id: Int?,
        val name: String?,
        val overview: String?,
        @SerializedName("poster_path")
        val posterPath: String?,
        @SerializedName("season_number")
        val seasonNumber: Int?,
        @SerializedName("show_id")
        val showId: Int?
)