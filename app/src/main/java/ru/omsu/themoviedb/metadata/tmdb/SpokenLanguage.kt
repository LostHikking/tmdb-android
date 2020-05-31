package ru.omsu.themoviedb.metadata.tmdb

import com.google.gson.annotations.SerializedName

data class SpokenLanguage(
        @SerializedName("iso_639_1")
        val iso6391: String,
        val name: String
)