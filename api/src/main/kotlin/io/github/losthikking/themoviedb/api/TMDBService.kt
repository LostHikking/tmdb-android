package io.github.losthikking.themoviedb.api

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import io.github.losthikking.themoviedb.api.dto.*
import io.github.losthikking.themoviedb.api.dto.movie.Collection
import io.github.losthikking.themoviedb.api.dto.movie.Movie
import io.github.losthikking.themoviedb.api.dto.tvshow.Episode
import io.github.losthikking.themoviedb.api.dto.tvshow.Season
import io.github.losthikking.themoviedb.api.dto.tvshow.TVShow
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDate

class Service(private val apiKey: String, private val language: String, private val region: String) {

    private val tmdbService = TMDBService.create()

    suspend fun getPopularMovies(page: Int) = tmdbService.getPopularMovies(apiKey, language, region, page)
    suspend fun getMovieDetails(id: Int) = tmdbService.getMovieDetails(apiKey, language, id)
    suspend fun getTvShowDetails(tvShowId: Int) = tmdbService.getTVshowDetails(apiKey, language, tvShowId)

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
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Path("movie_id") id: Int
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
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Path("movie_id") movieId: Int
    ): Videos

    @GET("movie/{movie_id}/credits")
    suspend fun getCreditsMovie(
            @Query("api_key") apiKey: String,
            @Path("movie_id") movieId: Int
    ): Credits

    @GET("tv/popular")
    suspend fun getPopularTV(
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Query("page") page: Int
    ): Page<TVShow>

    @GET("tv/top_rated")
    suspend fun getTopRatedTV(
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Query("page") page: Int
    ): Page<TVShow>

    @GET("tv/latest")
    suspend fun getLatestTV(
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Query("page") page: Int
    ): Page<TVShow>


    @GET("search/tv")
    suspend fun getSearchTVShows(
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Query("query") query: String,
            @Query("page") page: Int
    ): Page<TVShow>

    @GET("tv/{tvshow_id}")
    suspend fun getTVshowDetails(
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Path("tvshow_id") tvshowId: Int
    ): TVShow

    @GET("tv/{tvshow_id}/season/{season_number}")
    suspend fun getSeasonDetails(
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Path("tvshow_id") tvshowId: Int,
            @Path("season_number") seasonNumber: Int
    ): Season

    @GET("tv/{tvshow_id}/season/{season_number}/episode/{episode_number}")
    suspend fun getEpisodeDetails(
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Path("tvshow_id") tvshowId: Int,
            @Path("season_number") seasonNumber: Int,
            @Path("episode_number") episodeNumber: Int
    ): Episode

    @GET("tv/{tvshow_id}/videos")
    suspend fun getVideosTVShow(
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Path("tvshow_id") tvshowId: Int
    ): Videos

    @GET("person/{person_id}")
    suspend fun getPersonDetails(
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Path("person_id") personId: Int
    ): Person

    @GET("tv/{tvshow_id}/credits")
    suspend fun getCreditsTVShow(
            @Query("api_key") apiKey: String,
            @Path("tvshow_id") tvshowId: Int
    ): Credits

    @GET("collection/{collection_id}")
    suspend fun getCollectionDetails(
            @Query("api_key") apiKey: String,
            @Path("collection_id") collectionId: Int,
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
                            GsonConverterFactory.create(
                                    GsonBuilder()
                                            .setPrettyPrinting()
                                            .registerTypeAdapter(
                                                    LocalDate::class.java,
                                                    JsonDeserializer { json, _, _ ->
                                                        LocalDate.parse(json.asJsonPrimitive.asString)
                                                    })
                                            .create()
                            )
                    )
                    .build()
                    .create(TMDBService::class.java)
        }
    }

}