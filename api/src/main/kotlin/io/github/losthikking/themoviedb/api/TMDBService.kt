package io.github.losthikking.themoviedb.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.github.losthikking.themoviedb.api.dto.Credits
import io.github.losthikking.themoviedb.api.dto.Genre
import io.github.losthikking.themoviedb.api.dto.Page
import io.github.losthikking.themoviedb.api.dto.Person
import io.github.losthikking.themoviedb.api.dto.Videos
import io.github.losthikking.themoviedb.api.dto.movie.Collection
import io.github.losthikking.themoviedb.api.dto.movie.Movie
import io.github.losthikking.themoviedb.api.dto.tvshow.Episode
import io.github.losthikking.themoviedb.api.dto.tvshow.Season
import io.github.losthikking.themoviedb.api.dto.tvshow.TvShow
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDate

class Service(private val apiKey: String, private val language: String, private val region: String) {

    private val tmdbService = TMDBService.create()

    suspend fun getPopularMovies(page: Int) = tmdbService.getPopularMovies(apiKey, language, region, page)
    suspend fun getMovieDetails(id: Int) = tmdbService.getMovieDetails(id, apiKey, language)
    suspend fun getTvShowDetails(tvShowId: Int) = tmdbService.getTvShowDetails(tvShowId, apiKey, language)
}

interface TMDBService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("region") region: String,
        @Query("page") page: Int
    ): Page<Movie>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("region") region: String,
        @Query("page") page: Int
    ): Page<Movie>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("region") region: String,
        @Query("page") page: Int
    ): Page<Movie>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Movie

    @GET("search/movie")
    suspend fun getSearchMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query") query: String,
        @Query("include_adult") include_adult: Boolean = false,
        @Query("page") page: Int
    ): Page<Movie>

    @GET("movie/{movie_id}/videos")
    suspend fun getVideosMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Videos

    @GET("movie/{movie_id}/credits")
    suspend fun getCreditsMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Credits

    @GET("tv/popular")
    suspend fun getPopularTV(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Page<TvShow>

    @GET("tv/top_rated")
    suspend fun getTopRatedTV(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Page<TvShow>

    @GET("tv/latest")
    suspend fun getLatestTV(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Page<TvShow>

    @GET("search/tv")
    suspend fun getSearchTvShows(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): Page<TvShow>

    @GET("tv/{tv_show_id}")
    suspend fun getTvShowDetails(
        @Path("tv_show_id") tvShowId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): TvShow

    @GET("tv/{tv_show_id}/season/{season_number}")
    suspend fun getSeasonDetails(
        @Path("tv_show_id") tvShowId: Int,
        @Path("season_number") seasonNumber: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Season

    @GET("tv/{tv_show_id}/season/{season_number}/episode/{episode_number}")
    suspend fun getEpisodeDetails(
        @Path("tv_show_id") tvShowId: Int,
        @Path("season_number") seasonNumber: Int,
        @Path("episode_number") episodeNumber: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Episode

    @GET("tv/{tv_show_id}/videos")
    suspend fun getVideosTvShow(
        @Path("tv_show_id") tvShowId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Videos

    @GET("person/{person_id}")
    suspend fun getPersonDetails(
        @Path("person_id") personId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Person

    @GET("tv/{tv_show_id}/credits")
    suspend fun getCreditsTvShow(
        @Path("tv_show_id") tvShowId: Int,
        @Query("api_key") apiKey: String
    ): Credits

    @GET("collection/{collection_id}")
    suspend fun getCollectionDetails(
        @Path("collection_id") collectionId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Collection

    @GET("genre/movie/list")
    suspend fun getMovieGenres(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): List<Genre>

    @GET("genre/tv/list")
    suspend fun getTvGenres(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): List<Genre>

    companion object {

        fun create(): TMDBService {
            val logger = HttpLoggingInterceptor().apply { level = Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .client(client)
                .addConverterFactory(
                    Json { serializersModule = module }
                        .asConverterFactory("application/json".toMediaType())
                )
                .build()
                .create(TMDBService::class.java)
        }
    }
}

private object LocalDateSerializer : KSerializer<LocalDate> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        "LocalDate",
        PrimitiveKind.STRING
    )

    override fun serialize(encoder: Encoder, value: LocalDate) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): LocalDate {
        return LocalDate.parse(decoder.decodeString())
    }
}

private val module = SerializersModule {
    contextual(LocalDateSerializer)
}
