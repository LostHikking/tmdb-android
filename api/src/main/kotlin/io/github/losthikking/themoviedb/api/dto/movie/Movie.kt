package io.github.losthikking.themoviedb.api.dto.movie

import io.github.losthikking.themoviedb.api.dto.ContentItem
import io.github.losthikking.themoviedb.api.dto.Genre
import io.github.losthikking.themoviedb.api.dto.ProductionCompany
import io.github.losthikking.themoviedb.api.dto.ProductionCountry
import io.github.losthikking.themoviedb.api.dto.SpokenLanguage
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class Movie(
    val adult: Boolean? = null,
    @SerialName("backdrop_path")
    override val backdropPath: String? = null,
    @SerialName("belongs_to_collection")
    val belongsToCollection: Collection? = null,
    val budget: Int? = null,
    override val genres: List<Genre> = listOf(),
    val homepage: String? = null,
    override val id: Int,
    @SerialName("imdb_id")
    val imdbId: String? = null,
    @SerialName("original_language")
    val originalLanguage: String? = null,
    @SerialName("original_title")
    val originalTitle: String? = null,
    override val overview: String? = null,
    val popularity: Double? = null,
    @SerialName("poster_path")
    override val posterPath: String? = null,
    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompany> = listOf(),
    @SerialName("production_countries")
    val productionCountries: List<ProductionCountry> = listOf(),
    @SerialName("release_date")
    @Contextual
    override val releaseDate: LocalDate? = null,
    val revenue: Int? = null,
    val runtime: Int? = null,
    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage> = listOf(),
    val status: String? = null,
    val tagline: String? = null,
    override var title: String,
    val video: Boolean,
    @SerialName("vote_average")
    override val voteAverage: Float,
    @SerialName("vote_count")
    override val voteCount: Int,
    @SerialName("genre_ids")
    override val genresIds: List<Int> = listOf()
) : ContentItem()
