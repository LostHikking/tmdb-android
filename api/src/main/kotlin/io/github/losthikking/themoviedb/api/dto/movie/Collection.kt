package io.github.losthikking.themoviedb.api.dto.movie

import com.google.gson.annotations.SerializedName


data class Collection(
        @SerializedName("backdrop_path")
        val backdropPath: String?,
        val id: Int,
        val name: String?,
        val overview: String?,
        val parts: List<Movie>,
        @SerializedName("poster_path")
        val posterPath: String?
)