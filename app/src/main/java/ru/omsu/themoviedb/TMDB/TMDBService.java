package ru.omsu.themoviedb.TMDB;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.omsu.themoviedb.TMDB.Data.Movie;
import ru.omsu.themoviedb.TMDB.Data.MovieCredits;
import ru.omsu.themoviedb.TMDB.Data.MoviesPage;
import ru.omsu.themoviedb.TMDB.Data.VideoResult;

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

        @GET("movie/{id}")
        Call<Movie> getDetails(@Path("id") int id,
                               @Query("api_key") String api_key,
                               @Query("language") String language);

        @GET("movie/{id}/videos")
        Call<VideoResult> getVideos(@Path("id") int id,
                                    @Query("api_key") String api_key,
                                    @Query("language") String language);

        @GET("movie/{id}/credits")
        Call<MovieCredits> getCredits(@Path("id") int id,
                                      @Query("api_key") String api_key);
    }

}
