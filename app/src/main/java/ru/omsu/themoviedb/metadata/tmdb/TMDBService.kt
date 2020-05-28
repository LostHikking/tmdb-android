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
import ru.omsu.themoviedb.metadata.tmdb.tvshow.Episode
import ru.omsu.themoviedb.metadata.tmdb.tvshow.Season
import ru.omsu.themoviedb.metadata.tmdb.tvshow.TVShow

interface TMDBService {

    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") api_key: String,
                         @Query("language") language: String,
                         @Query("page") page: Int,
                         @Query("region") region: String?): Observable<Page<Movie>>

    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("api_key") api_key: String,
                          @Query("language") language: String?,
                          @Query("page") page: Int,
                          @Query("region") region: String?): Observable<Page<Movie>>

    @GET("movie/upcoming")
    fun getUpcomingMovies(@Query("api_key") api_key: String,
                          @Query("language") language: String?,
                          @Query("page") page: Int,
                          @Query("region") region: String?): Observable<Page<Movie>>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movie_id: Int,
                        @Query("api_key") api_key: String,
                        @Query("language") language: String?): Observable<Movie>
	
	@GET("search/movie")
	fun getSearchMovies(@Query("api_key") api_key: String,
	                    @Query("language") language: String?,
	                    @Query("query") query: String,
	                    @Query("include_adult") include_adult: Boolean = true,
	                    @Query("page") page: Int): Observable<Page<Movie>>

    @GET("movie/{movie_id}/videos")
    fun getVideosMovie(@Path("movie_id") movie_id: Int,
                       @Query("api_key") api_key: String,
                       @Query("language") language: String?): Observable<Videos>

    @GET("movie/{movie_id}/credits")
    fun getCreditsMovie(@Path("movie_id") movie_id: Int,
                        @Query("api_key") api_key: String): Observable<Credits>

    @GET("tv/popular")
    fun getPopularTV(@Query("api_key") api_key: String,
                     @Query("language") language: String,
                     @Query("page") page: Int): Observable<Page<TVShow>>

    @GET("tv/top_rated")
    fun getTopRatedTV(@Query("api_key") api_key: String,
                      @Query("language") language: String?,
                      @Query("page") page: Int): Observable<Page<TVShow>>

    @GET("tv/latest")
    fun getLatestTV(@Query("api_key") api_key: String,
                    @Query("language") language: String?,
                    @Query("page") page: Int): Observable<Page<TVShow>>
	
	
	@GET("search/tv")
	fun getSearchTVShows(@Query("api_key") api_key: String,
	                     @Query("language") language: String?,
	                     @Query("query") query: String,
	                     @Query("page") page: Int): Observable<Page<TVShow>>
    
    @GET("tv/{tvshow_id}")
    fun getTVshowDetails(@Path("tvshow_id") tvshow_id: Int,
                         @Query("api_key") api_key: String,
                         @Query("language") language: String?): Observable<TVShow>

    @GET("tv/{tvshow_id}/season/{season_number}")
    fun getSeasonDetails(@Path("tvshow_id") tvshow_id: Int,
                         @Path("season_number") season_number: Int,
                         @Query("api_key") api_key: String,
                         @Query("language") language: String?): Observable<Season>

    @GET("tv/{tvshow_id}/season/{season_number}/episode/{episode_number}")
    fun getEpisodeDetails(@Path("tvshow_id") tvshow_id: Int,
                          @Path("season_number") season_number: Int,
                          @Path("episode_number") episode_number: Int,
                          @Query("api_key") api_key: String,
                          @Query("language") language: String?): Observable<Episode>

    @GET("tv/{tvshow_id}/videos")
    fun getVideosTVShow(@Path("tvshow_id") tvshow_id: Int,
                        @Query("api_key") api_key: String,
                        @Query("language") language: String?): Observable<Videos>

    @GET("person/{person_id}")
    fun getPersonDetails(@Path("person_id") person_id: Int,
                         @Query("api_key") api_key: String,
                         @Query("language") language: String?): Observable<Person>

    @GET("tv/{tvshow_id}/credits")
    fun getCreditsTVShow(@Path("tvshow_id") tvshow_id: Int,
                         @Query("api_key") api_key: String): Observable<Credits>

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