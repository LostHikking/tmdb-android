package ru.omsu.themoviedb.metadata.tmdb.tvshow

import ru.omsu.themoviedb.metadata.tmdb.Crew
import ru.omsu.themoviedb.metadata.tmdb.Genre
import ru.omsu.themoviedb.metadata.tmdb.Network
import ru.omsu.themoviedb.metadata.tmdb.ProductionCompany


data class TVShow(
        val backdrop_path: String? = null,
        val created_by: List<Crew?>? = null,
        val episode_run_time: List<Int?>? = null,
        val first_air_date: String? = null,
        val genres: List<Genre?>? = null,
        val homepage: String? = null,
        val id: Int? = null,
        val in_production: Boolean? = null,
        val languages: List<String?>? = null,
        val last_air_date: String? = null,
        val last_episode_to_air: Episode? = null,
        val name: String? = null,
        val networks: List<Network?>? = null,
        val next_episode_to_air: Any? = null,
        val number_of_episodes: Int? = null,
        val number_of_seasons: Int? = null,
        val origin_country: List<String?>? = null,
        val original_language: String? = null,
        val original_name: String? = null,
        val overview: String? = null,
        val popularity: Double? = null,
        val poster_path: String? = null,
        val production_companies: List<ProductionCompany?>? = null,
        val seasons: List<Season?>? = null,
        val status: String? = null,
        val type: String? = null,
        val vote_average: Double? = null,
        val vote_count: Int? = null
)