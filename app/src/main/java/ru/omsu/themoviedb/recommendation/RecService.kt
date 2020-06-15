package ru.omsu.themoviedb.recommendation

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path


interface RecService {
    @GET("api/user/")
    fun getUserInfo(@Header("Authorization") auth: String): Call<UserDTO>

    @POST("/score/{movieId}/{score}")
    fun postScore(@Header("Authorization") auth: String,
                  @Path("movieId") movieId: Long,
                  @Path("score") score: Int): Call<Void>

    companion object Factory {
        fun create(): RecService {
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://192.168.0.5:8080/")
                    .build()

            return retrofit.create(RecService::class.java)
        }
    }
}