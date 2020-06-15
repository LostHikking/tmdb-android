package ru.omsu.themoviedb.metadata.tmdb.movie

import com.google.gson.annotations.SerializedName
import ru.omsu.themoviedb.metadata.tmdb.Genre
import ru.omsu.themoviedb.metadata.tmdb.ProductionCompany
import ru.omsu.themoviedb.metadata.tmdb.ProductionCountry
import ru.omsu.themoviedb.metadata.tmdb.SpokenLanguage

data class Movie(
        val adult: Boolean?,
        @SerializedName("backdrop_path")
		val backdropPath: String?,
        @SerializedName("belongs_to_collection")
		val belongsToCollection: Collection?,
        val budget: Int?,
        val genres: List<Genre>,
        val homepage: String?,
        val id: Int,
        @SerializedName("imdb_id")
		val imdbId: String?,
        @SerializedName("original_language")
		val originalLanguage: String?,
        @SerializedName("original_title")
        val originalTitle: String?,
        val overview: String?,
        val popularity: Double?,
        @SerializedName("poster_path")
        val posterPath: String?,
        @SerializedName("production_companies")
        val productionCompanies: List<ProductionCompany>,
        @SerializedName("production_countries")
        val productionCountries: List<ProductionCountry>,
        @SerializedName("release_date")
        val releaseDate: String?,
        val revenue: Int?,
        val runtime: Int?,
        @SerializedName("spoken_languages")
        val spokenLanguages: List<SpokenLanguage>,
        val status: String?,
        val tagline: String?,
        var title: String,
        val video: Boolean,
        @SerializedName("vote_average")
        val voteAverage: Double,
        @SerializedName("vote_count")
		val voteCount: Int
)