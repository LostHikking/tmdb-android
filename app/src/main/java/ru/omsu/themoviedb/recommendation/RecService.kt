package ru.omsu.themoviedb.recommendation

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.omsu.themoviedb.metadata.tmdb.TMDBService

interface RecService {
    //TODO


    companion object Factory {
        fun create(): RecService {
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("192.168.0.5:8080/")
                    .build()

            return retrofit.create(RecService::class.java)
        }
    }
}