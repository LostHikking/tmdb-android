package ru.omsu.themoviedb.metadata.tmdb

data class PersonPage(
        val page: Int? = null,
        val people: List<Person?>? = null,
        val total_pages: Int? = null,
        val total_results: Int? = null
)