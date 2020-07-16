package ru.omsu.themoviedb.api.tmdb.dto

import com.google.gson.annotations.SerializedName

data class ProductionCountry(
        @SerializedName("iso_3166_1")
        val iso31661: String,
        val name: String
)