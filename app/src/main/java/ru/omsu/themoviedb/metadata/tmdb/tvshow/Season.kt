package ru.omsu.themoviedb.metadata.tmdb.tvshow


data class Season(
        val _id: String?,
        val air_date: String?,
        val episodes: List<Episode>,
        val episode_count: Int?,
        val id: Int?,
        val name: String?,
        val overview: String?,
        val poster_path: String?,
        val season_number: Int?,
        val show_id: Int?
)