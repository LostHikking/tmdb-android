package ru.omsu.themoviedb.metadata.tmdb

import ru.omsu.themoviedb.adapters.Result

data class Videos(
        val id: Int? = null,
        val results: List<Result?>? = null
)