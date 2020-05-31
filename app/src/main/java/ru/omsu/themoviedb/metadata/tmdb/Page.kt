package ru.omsu.themoviedb.metadata.tmdb

import com.google.gson.annotations.SerializedName

data class Page<T>(
        val page: Int,
        val results: List<T?>,
        @SerializedName("total_pages")
        val totalPages: Int,
        @SerializedName("total_results")
        val totalResults: Int
)