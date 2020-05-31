package ru.omsu.themoviedb.metadata.tmdb.tvshow

import com.google.gson.annotations.SerializedName
import ru.omsu.themoviedb.metadata.tmdb.Actor
import ru.omsu.themoviedb.metadata.tmdb.Crew

data class Episode(
        @SerializedName("air_date")
        val air_date: String?,
        val crew: List<Crew>,
        val episode_number: Int? = null,
        @SerializedName("guest_stars")
        val guest_stars: List<Actor>,
        val id: Int,
        val name: String?,
        val overview: String?,
        @SerializedName("production_code")
        val productionCode: String?,
        @SerializedName("season_number")
        val seasonNumber: Int?,
        @SerializedName("show_id")
        val showId: Int?,
        @SerializedName("still_path")
        val stillPath: String?,
        @SerializedName("vote_average")
        val voteAverage: Double,
        @SerializedName("vote_count")
        val voteCount: Int
)