package ru.omsu.themoviedb.metadata.tmdb.tvshow


data class Season(
        val _id: String? = null,
        val air_date: String? = null,
        val episodes: List<Episode?>? = null,
        val episode_count: Int? = null,
        val id: Int? = null,
        val name: String? = null,
        val overview: String? = null,
        val poster_path: String? = null,
        val season_number: Int? = null,
        val show_id: Int? = null
)