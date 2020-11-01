package io.github.losthikking.themoviedb.api

import io.github.losthikking.themoviedb.api.dto.Page
import io.github.losthikking.themoviedb.api.dto.movie.Movie
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.StringSpec
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString

class SerializationTest : StringSpec({
    val movieJsonString = javaClass.classLoader.getResource("movie_detail.json")!!.readText()

    "Movie" {
        shouldNotThrow<SerializationException> {
            json.decodeFromString<Movie>(movieJsonString)
        }
    }

    val popularMoviesJsonString =
        javaClass.classLoader.getResource("popular_movies.json")!!.readText()

    "Movie Page" {
        shouldNotThrow<SerializationException> {
            json.decodeFromString<Page<Movie>>(popularMoviesJsonString)
        }
    }
})
