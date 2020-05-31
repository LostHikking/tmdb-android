package ru.omsu.themoviedb.metadata.tmdb

import com.google.gson.annotations.SerializedName

data class Network(
        val id: Int,
        @SerializedName("logo_path")
        val logoPath: String?,
        val name: String,
        @SerializedName("origin_country")
        val originCountry: String?
)