package com.example.primeraclaseandroid.builder

import com.example.primeraclaseandroid.`interface`.MovieEndPoint
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun serviciosPeliculas() : MovieEndPoint {
        return retrofit.create(MovieEndPoint::class.java)
    }
}
