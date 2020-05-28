package ru.omsu.themoviedb.metadata.tmdb.tvshow

import ru.omsu.themoviedb.metadata.tmdb.Crew
import ru.omsu.themoviedb.metadata.tmdb.Genre
import ru.omsu.themoviedb.metadata.tmdb.Network
import ru.omsu.themoviedb.metadata.tmdb.ProductionCompany


data class TVShow(
        val backdrop_path: String?,
        val created_by: List<Crew>,
        val episode_run_time: List<Int>,
        val first_air_date: String?,
        val genres: List<Genre?>?,
        val homepage: String?,
        val id: Int?,
        val in_production: Boolean?,
        val languages: List<String>,
        val last_air_date: String?,
        val last_episode_to_air: Episode?,
        val name: String? = null,
        val networks: List<Network>,
        val next_episode_to_air: Any?,
        val number_of_episodes: Int?,
        val number_of_seasons: Int?,
        val origin_country: List<String>,
        val original_language: String?,
        val original_name: String?,
        val overview: String?,
        val popularity: Double?,
        val poster_path: String?,
        val production_companies: List<ProductionCompany>,
        val seasons: List<Season>,
        val status: String?,
        val type: String?,
        val vote_average: Double,
        val vote_count: Int
)