package ru.omsu.themoviedb.Metadata.TMDB;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.omsu.themoviedb.Metadata.TMDB.Item.Movie.Collection;
import ru.omsu.themoviedb.Metadata.TMDB.Item.Movie.Movie;
import ru.omsu.themoviedb.Metadata.TMDB.Item.Movie.MoviesPage;
import ru.omsu.themoviedb.Metadata.TMDB.Item.TVShow.TVShowsPage;

public class TMDBService {
    private static TMDBService mInstance;
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private Retrofit mRetrofit;

    private TMDBService() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static TMDBService getInstance() {
        if (mInstance == null) {
            mInstance = new TMDBService();
        }
        return mInstance;
    }

    public JSONPlaceHolderApi getJSONApi() {
        return mRetrofit.create(JSONPlaceHolderApi.class);
    }

    public interface JSONPlaceHolderApi {
        @GET("movie/popular")
        Call<MoviesPage> getPopularMovies(@Query("api_key") String api_key,
                                          @Query("language") String language,
                                          @Query("page") int page,
                                          @Query("region") String region);
        @GET("movie/top_rated")
        Call<MoviesPage> getTopRatedMovies(@Query("api_key") String api_key,
                                           @Query("language") String language,
                                           @Query("page") int page,
                                           @Query("region") String region);
        @GET("movie/upcoming")
        Call<MoviesPage> getUpcomingMovies(@Query("api_key") String api_key,
                                           @Query("language") String language,
                                           @Query("page") int page,
                                           @Query("region") String region);

        @GET("movie/{movie_id}")
        Call<Movie> getMovieDetails(@Path("movie_id") int movie_id,
                                    @Query("api_key") String api_key,
                                    @Query("language") String language);

        @GET("movie/{movie_id}/videos")
        Call<Videos> getVideos(@Path("movie_id") int movie_id,
                               @Query("api_key") String api_key,
                               @Query("language") String language);

        @GET("movie/{movie_id}/credits")
        Call<MovieCredits> getCredits(@Path("movie_id") int movie_id,
                                      @Query("api_key") String api_key);

        @GET("tv/popular")
        Call<TVShowsPage> getPopularTV(@Query("api_key") String api_key,
                                       @Query("language") String language,
                                       @Query("page") int page);

        @GET("tv/top_rated")
        Call<TVShowsPage> getTopRatedTV(@Query("api_key") String api_key,
                                        @Query("language") String language,
                                        @Query("page") int page);

        @GET("tv/latest")
        Call<TVShowsPage> getLatestTV(@Query("api_key") String api_key,
                                      @Query("language") String language,
                                      @Query("page") int page);

        @GET("collection/{collection_id}")
        Call<Collection> getCollectionDetails(@Path("collection_id") int collection_id,
                                              @Query("api_key") String api_key,
                                              @Query("language") String language);
    }

}
