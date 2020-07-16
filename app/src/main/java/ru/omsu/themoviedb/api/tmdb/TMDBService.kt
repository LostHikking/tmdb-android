package ru.omsu.themoviedb.api.tmdb

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.omsu.themoviedb.API_KEY
import ru.omsu.themoviedb.api.tmdb.dto.Credits
import ru.omsu.themoviedb.api.tmdb.dto.Page
import ru.omsu.themoviedb.api.tmdb.dto.Person
import ru.omsu.themoviedb.api.tmdb.dto.Videos
import ru.omsu.themoviedb.api.tmdb.dto.movie.Collection
import ru.omsu.themoviedb.api.tmdb.dto.movie.Movie
import ru.omsu.themoviedb.api.tmdb.dto.tvshow.Episode
import ru.omsu.themoviedb.api.tmdb.dto.tvshow.Season
import ru.omsu.themoviedb.api.tmdb.dto.tvshow.TVShow
import java.time.LocalDate
import java.util.*


interface TMDBService {
    private val apiKey: String
        get() = API_KEY
    private val language: String
        get() = Locale.getDefault().toLanguageTag()
    private val region: String
        get() = Locale.getDefault().country

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String = this.apiKey,
                                 @Query("language") language: String = this.language,
                                 @Query("page") page: Int,
                                 @Query("region") region: String = this.region): Page<Movie>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("api_key") apiKey: String,
                                  @Query("language") language: String = this.language,
                                  @Query("page") page: Int,
                                  @Query("region") region: String = this.region): Page<Movie>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("api_key") apiKey: String,
                                  @Query("language") language: String = this.language,
                                  @Query("page") page: Int,
                                  @Query("region") region: String = this.region): Page<Movie>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") id: Int,
                                @Query("api_key") apiKey: String = this.apiKey,
                                @Query("language") language: String = this.language): Movie

    @GET("search/movie")
    suspend fun getSearchMovies(@Query("api_key") apiKey: String,
                                @Query("language") language: String = this.language,
                                @Query("query") query: String,
                                @Query("include_adult") include_adult: Boolean = false,
                                @Query("page") page: Int): Page<Movie>

    @GET("movie/{movie_id}/videos")
    suspend fun getVideosMovie(@Path("movie_id") movieId: Int,
                               @Query("api_key") apiKey: String,
                               @Query("language") language: String = this.language): Videos

    @GET("movie/{movie_id}/credits")
    suspend fun getCreditsMovie(@Path("movie_id") movieId: Int,
                                @Query("api_key") apiKey: String): Credits

    @GET("tv/popular")
    suspend fun getPopularTV(@Query("api_key") apiKey: String = this.apiKey,
                             @Query("language") language: String = this.language,
                             @Query("page") page: Int): Page<TVShow>

    @GET("tv/top_rated")
    suspend fun getTopRatedTV(@Query("api_key") apiKey: String,
                              @Query("language") language: String = this.language,
                              @Query("page") page: Int): Page<TVShow>

    @GET("tv/latest")
    suspend fun getLatestTV(@Query("api_key") apiKey: String,
                            @Query("language") language: String = this.language,
                            @Query("page") page: Int): Page<TVShow>


    @GET("search/tv")
    suspend fun getSearchTVShows(@Query("api_key") apiKey: String,
                                 @Query("language") language: String = this.language,
                                 @Query("query") query: String,
                                 @Query("page") page: Int): Page<TVShow>

    @GET("tv/{tvshow_id}")
    suspend fun getTVshowDetails(@Path("tvshow_id") tvshowId: Int,
                                 @Query("api_key") apiKey: String = API_KEY,
                                 @Query("language") language: String = this.language): TVShow

    @GET("tv/{tvshow_id}/season/{season_number}")
    suspend fun getSeasonDetails(@Path("tvshow_id") tvshowId: Int,
                                 @Path("season_number") seasonNumber: Int,
                                 @Query("api_key") apiKey: String,
                                 @Query("language") language: String = this.language): Season

    @GET("tv/{tvshow_id}/season/{season_number}/episode/{episode_number}")
    suspend fun getEpisodeDetails(@Path("tvshow_id") tvshowId: Int,
                                  @Path("season_number") seasonNumber: Int,
                                  @Path("episode_number") episodeNumber: Int,
                                  @Query("api_key") apiKey: String,
                                  @Query("language") language: String = this.language): Episode

    @GET("tv/{tvshow_id}/videos")
    suspend fun getVideosTVShow(@Path("tvshow_id") tvshowId: Int,
                                @Query("api_key") apiKey: String,
                                @Query("language") language: String = this.language): Videos

    @GET("person/{person_id}")
    suspend fun getPersonDetails(@Path("person_id") personId: Int,
                                 @Query("api_key") apiKey: String,
                                 @Query("language") language: String = this.language): Person

    @GET("tv/{tvshow_id}/credits")
    suspend fun getCreditsTVShow(@Path("tvshow_id") tvshowId: Int,
                                 @Query("api_key") apiKey: String): Credits

    @GET("collection/{collection_id}")
    suspend fun getCollectionDetails(@Path("collection_id") collectionId: Int,
                                     @Query("api_key") apiKey: String,
                                     @Query("language") language: String = this.language): Collection


    companion object {

        fun create(): TMDBService {
            val logger = HttpLoggingInterceptor().apply { level = Level.BASIC }

            val client = OkHttpClient.Builder()
                    .addInterceptor(logger)
                    .build()

            return Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(GsonBuilder()
                            .setPrettyPrinting()
                            .registerTypeAdapter(
                                    LocalDate::class.java,
                                    JsonDeserializer { json, _, _ ->
                                        LocalDate.parse(json.asJsonPrimitive.asString)
                                    })
                            .create()))
                    .build()
                    .create(TMDBService::class.java)
        }
    }

}