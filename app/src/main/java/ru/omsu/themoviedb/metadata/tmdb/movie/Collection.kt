package ru.omsu.themoviedb.metadata.tmdb.movie


data class Collection(
        val backdrop_path: String? = null,
        val id: Int? = null,
        val name: String? = null,
        val overview: String? = null,
        val parts: List<Movie?>? = null,
        val poster_path: String? = null
)