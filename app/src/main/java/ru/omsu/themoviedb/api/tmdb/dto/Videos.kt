package ru.omsu.themoviedb.api.tmdb.dto

data class Videos(
        val id: Int,
        val results: List<Video>
)