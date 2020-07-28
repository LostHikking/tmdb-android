package io.github.losthikking.themoviedb.api.tmdb.dto

import java.time.LocalDate

abstract class ContentItem {
    abstract val id: Int
    abstract val releaseDate: LocalDate?
    abstract val posterPath: String?
    abstract val backdropPath: String?
    abstract val voteAverage: Float?
    abstract val overview: String?
    abstract val title: String
    abstract val genres: List<Genre>
}
