package ru.omsu.themoviedb.metadata.tmdb.movie

data class MoviePage(
        val page: Int? = null,
        val results: List<Movie?>? = null,
        val total_pages: Int? = null,
        val total_results: Int? = null
)