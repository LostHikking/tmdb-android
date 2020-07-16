package ru.omsu.themoviedb.api.tmdb.dto.tvshow

import com.google.gson.annotations.SerializedName
import ru.omsu.themoviedb.api.tmdb.dto.*
import java.time.LocalDate


data class TVShow(
        @SerializedName("backdrop_path")
        override val backdropPath: String?,
        @SerializedName("created_by")
        val createdBy: List<Crew>,
        @SerializedName("episode_run_time")
        val episodeRunTime: List<Int>,
        @SerializedName("first_air_date")
        override val releaseDate: LocalDate?,
        override val genres: List<Genre>,
        val homepage: String?,
        override val id: Int,
        @SerializedName("in_production")
        val inProduction: Boolean?,
        val languages: List<String>,
        @SerializedName("last_air_date")
        val lastAirDate: String?,
        @SerializedName("last_episode_to_air")
        val lastEpisodeToAir: Episode?,
        @SerializedName("name")
        override val title: String,
        val networks: List<Network>,
        @SerializedName("next_episode_to_air")
        val nextEpisodeToAir: Any?,
        @SerializedName("number_of_episodes")
        val numberOfEpisodes: Int?,
        @SerializedName("number_of_seasons")
        val numberOfSeasons: Int?,
        @SerializedName("origin_country")
        val originCountry: List<String>,
        @SerializedName("original_language")
        val originalLanguage: String?,
        @SerializedName("original_name")
        val originalName: String?,
        override val overview: String?,
        val popularity: Double?,
        @SerializedName("poster_path")
        override val posterPath: String?,
        @SerializedName("production_companies")
        val productionCompanies: List<ProductionCompany>,
        val seasons: List<Season>,
        val status: String?,
        val type: String?,
        @SerializedName("vote_average")
        override val voteAverage: Float,
        @SerializedName("vote_count")
        val voteCount: Int
) : ContentItem()