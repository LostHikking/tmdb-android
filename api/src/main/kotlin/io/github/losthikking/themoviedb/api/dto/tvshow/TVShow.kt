package io.github.losthikking.themoviedb.api.dto.tvshow

import io.github.losthikking.themoviedb.api.dto.*
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class TVShow(
        @SerialName("backdrop_path")
        override val backdropPath: String? = null,
        @SerialName("created_by")
        val createdBy: List<Crew>,
        @SerialName("episode_run_time")
        val episodeRunTime: List<Int>,
        @Contextual
        @SerialName("first_air_date")
        override val releaseDate: LocalDate? = null,
        override val genres: List<Genre>,
        val homepage: String? = null,
        override val id: Int,
        @SerialName("in_production")
        val inProduction: Boolean? = null,
        val languages: List<String>,
        @SerialName("last_air_date")
        val lastAirDate: String? = null,
        @SerialName("last_episode_to_air")
        val lastEpisodeToAir: Episode? = null,
        @SerialName("name")
        override val title: String,
        val networks: List<Network>,
        @SerialName("next_episode_to_air")
        val nextEpisodeToAir: Episode? = null,
        @SerialName("number_of_episodes")
        val numberOfEpisodes: Int? = null,
        @SerialName("number_of_seasons")
        val numberOfSeasons: Int? = null,
        @SerialName("origin_country")
        val originCountry: List<String>,
        @SerialName("original_language")
        val originalLanguage: String? = null,
        @SerialName("original_name")
        val originalName: String? = null,
        override val overview: String? = null,
        val popularity: Double? = null,
        @SerialName("poster_path")
        override val posterPath: String? = null,
        @SerialName("production_companies")
        val productionCompanies: List<ProductionCompany>,
        val seasons: List<Season>,
        val status: String? = null,
        val type: String? = null,
        @SerialName("vote_average")
        override val voteAverage: Float,
        @SerialName("vote_count")
        override val voteCount: Int,
        @SerialName("genre_ids")
        override val genresIds: List<Int>
) : ContentItem()