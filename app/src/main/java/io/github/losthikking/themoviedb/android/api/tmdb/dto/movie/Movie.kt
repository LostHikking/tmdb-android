package io.github.losthikking.themoviedb.android.api.tmdb.dto.movie

import com.google.gson.annotations.SerializedName
import io.github.losthikking.themoviedb.android.api.tmdb.dto.*
import java.time.LocalDate

data class Movie(
        val adult: Boolean?,
        @SerializedName("backdrop_path")
        override val backdropPath: String?,
        @SerializedName("belongs_to_collection")
        val belongsToCollection: Collection?,
        val budget: Int?,
        override val genres: List<Genre>,
        val homepage: String?,
        override val id: Int,
        @SerializedName("imdb_id")
        val imdbId: String?,
        @SerializedName("original_language")
        val originalLanguage: String?,
        @SerializedName("original_title")
        val originalTitle: String?,
        override val overview: String?,
        val popularity: Double?,
        @SerializedName("poster_path")
        override val posterPath: String?,
        @SerializedName("production_companies")
        val productionCompanies: List<ProductionCompany>,
        @SerializedName("production_countries")
        val productionCountries: List<ProductionCountry>,
        @SerializedName("release_date")
        override val releaseDate: LocalDate?,
        val revenue: Int?,
        val runtime: Int?,
        @SerializedName("spoken_languages")
        val spokenLanguages: List<SpokenLanguage>,
        val status: String?,
        val tagline: String?,
        override var title: String,
        val video: Boolean,
        @SerializedName("vote_average")
        override val voteAverage: Float,
        @SerializedName("vote_count")
        override val voteCount: Int,
        @SerializedName("genre_ids")
        override val genresIds: List<Int>
) : ContentItem()