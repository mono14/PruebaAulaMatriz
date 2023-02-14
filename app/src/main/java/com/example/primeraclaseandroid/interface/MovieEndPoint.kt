package com.example.primeraclaseandroid.`interface`

import com.example.primeraclaseandroid.data.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieEndPoint {

    @GET("/3/discover/movie?language=es&sort_by=popularity.desc")
    fun getMovie(
        @Query("api_key")
        apikey : String,
        @Query("page")
        page : Int
    ) : Call<MovieResponse>

}