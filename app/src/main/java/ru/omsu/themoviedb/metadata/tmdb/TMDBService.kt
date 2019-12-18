package ru.omsu.themoviedb.metadata.tmdb

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.omsu.themoviedb.metadata.tmdb.movie.Collection
import ru.omsu.themoviedb.metadata.tmdb.movie.Movie
import ru.omsu.themoviedb.metadata.tmdb.movie.MoviePage
import ru.omsu.themoviedb.metadata.tmdb.tvshow.TVShowPage

interface TMDBService {

    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") api_key: String,
                         @Query("language") language: String,
                         @Query("page") page: Int,
                         @Query("region") region: String?): Observable<MoviePage>

    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("api_key") api_key: String,
                          @Query("language") language: String?,
                          @Query("page") page: Int,
                          @Query("region") region: String?): Observable<MoviePage>

    @GET("movie/upcoming")
    fun getUpcomingMovies(@Query("api_key") api_key: String,
                          @Query("language") language: String?,
                          @Query("page") page: Int,
                          @Query("region") region: String?): Observable<MoviePage>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movie_id: Int,
                        @Query("api_key") api_key: String,
                        @Query("language") language: String?): Observable<Movie>

    @GET("movie/{movie_id}/videos")
    fun getVideos(@Path("movie_id") movie_id: Int,
                  @Query("api_key") api_key: String,
                  @Query("language") language: String?): Observable<Videos>

    @GET("movie/{movie_id}/credits")
    fun getCredits(@Path("movie_id") movie_id: Int,
                   @Query("api_key") api_key: String): Observable<Credits>

    @GET("tv/popular")
    fun getPopularTV(@Query("api_key") api_key: String,
                     @Query("language") language: String,
                     @Query("page") page: Int): Observable<TVShowPage>

    @GET("tv/top_rated")
    fun getTopRatedTV(@Query("api_key") api_key: String,
                      @Query("language") language: String?,
                      @Query("page") page: Int): Observable<TVShowPage>

    @GET("tv/latest")
    fun getLatestTV(@Query("api_key") api_key: String,
                    @Query("language") language: String?,
                    @Query("page") page: Int): Observable<TVShowPage>

    @GET("collection/{collection_id}")
    fun getCollectionDetails(@Path("collection_id") collection_id: Int,
                             @Query("api_key") api_key: String,
                             @Query("language") language: String?): Observable<Collection>


    companion object Factory {

        fun create(): TMDBService {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.themoviedb.org/3/")
                    .build()

            return retrofit.create(TMDBService::class.java)
        }
    }

}