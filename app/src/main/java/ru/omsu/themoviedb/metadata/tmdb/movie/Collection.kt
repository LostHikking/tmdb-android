package ru.omsu.themoviedb.metadata.tmdb.movie


data class Collection(
        val backdrop_path: String?,
        val id: Int,
        val name: String?,
        val overview: String?,
        val parts: List<Movie>,
        val poster_path: String?
)