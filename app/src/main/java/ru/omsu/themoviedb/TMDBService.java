package ru.omsu.themoviedb;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
        public Call<ResultsFromTMDB> getPopularMovies(@Query("api_key") String api_key,
                                                      @Query("language") String language,
                                                      @Query("page") int page,
                                                      @Query("region") String region);
        @GET("movie/top_rated")
        public Call<ResultsFromTMDB> getTopRatedMovies(@Query("api_key") String api_key,
                                                      @Query("language") String language,
                                                      @Query("page") int page,
                                                      @Query("region") String region);
        @GET("movie/upcoming")
        public Call<ResultsFromTMDB> getUpcomingMovies(@Query("api_key") String api_key,
                                                       @Query("language") String language,
                                                       @Query("page") int page,
                                                       @Query("region") String region);
    }

}
