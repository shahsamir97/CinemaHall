package com.mdshahsamir.ovisharcinemahall.network

import com.mdshahsamir.ovisharcinemahall.model.TopRatedMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPIService {

    @GET("movie/top_rated")
    suspend fun fetchTopRatedMovies(@Query("language") language: String = "en-US", @Query("page") page: Int = 1)
            : TopRatedMoviesResponse
}