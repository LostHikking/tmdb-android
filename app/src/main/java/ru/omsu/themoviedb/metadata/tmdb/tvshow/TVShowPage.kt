package ru.omsu.themoviedb.metadata.tmdb.tvshow

data class TVShowPage(
        val page: Int? = null,
        val results: List<TVShow?>? = null,
        val total_pages: Int? = null,
        val total_results: Int? = null
)