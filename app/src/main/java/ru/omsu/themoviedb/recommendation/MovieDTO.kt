package ru.omsu.themoviedb.recommendation

interface MovieDTO {
    val id: Int
    val title: String
    val overview: String?
    val voteAverage: Double
    val voteCount: Int
    val posterPath: String?
    val backdropPath: String?
}
