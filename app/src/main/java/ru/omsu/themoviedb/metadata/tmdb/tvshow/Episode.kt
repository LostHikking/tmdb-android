package ru.omsu.themoviedb.metadata.tmdb.tvshow

import ru.omsu.themoviedb.metadata.tmdb.Actor
import ru.omsu.themoviedb.metadata.tmdb.Crew

data class Episode(
        val air_date: String? = null,
        val crew: List<Crew?>? = null,
        val episode_number: Int? = null,
        val guest_stars: List<Actor?>? = null,
        val id: Int? = null,
        val name: String? = null,
        val overview: String? = null,
        val production_code: String? = null,
        val season_number: Int? = null,
        val show_id: Int? = null,
        val still_path: String? = null,
        val vote_average: Double? = null,
        val vote_count: Int? = null
)