package ru.omsu.themoviedb.recommendation

data class RecommendationDTO(
        val type: String,
        override val id: Int,
        override val title: String,
        override val overview: String?,
        override val voteAverage: Double,
        override val posterPath: String?,
        override val backdropPath: String?,
        override val voteCount: Int
) : MovieDTO