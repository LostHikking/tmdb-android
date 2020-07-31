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
    abstract val genresIds: List<Int>
    abstract val voteCount: Int
    fun getPrettyRating(): String {
        return "${getVoteAverage(5)} ($voteCount)"
    }

    fun getVoteAverage(max: Int): Float? {
        return voteAverage?.div(10 / max)
    }
}
