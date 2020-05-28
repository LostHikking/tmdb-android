package ru.omsu.themoviedb.metadata.tmdb.movie

import ru.omsu.themoviedb.metadata.tmdb.Genre
import ru.omsu.themoviedb.metadata.tmdb.ProductionCompany
import ru.omsu.themoviedb.metadata.tmdb.ProductionCountry
import ru.omsu.themoviedb.metadata.tmdb.SpokenLanguage

data class Movie(
		val adult: Boolean?,
		val backdrop_path: String?,
		val belongs_to_collection: Collection?,
		val budget: Int?,
		val genres: List<Genre>,
		val homepage: String?,
		val id: Int,
		val imdb_id: String?,
		val original_language: String?,
		val original_title: String?,
		val overview: String?,
		val popularity: Double?,
		val poster_path: String?,
		val production_companies: List<ProductionCompany>,
		val production_countries: List<ProductionCountry>,
		val release_date: String?,
		val revenue: Int?,
		val runtime: Int?,
		val spoken_languages: List<SpokenLanguage>,
		val status: String?,
		val tagline: String?,
		var title: String,
		val video: Boolean,
		val vote_average: Double,
		val vote_count: Int
)