package com.mdshahsamir.ovisharcinemahall.network

import com.mdshahsamir.ovisharcinemahall.model.MovieDetailsResponse
import com.mdshahsamir.ovisharcinemahall.model.ListOfMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPIService {

    @GET("movie/top_rated")
    suspend fun fetchTopRatedMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): ListOfMoviesResponse

    @GET("movie/{movie_id}")
    suspend fun fetchMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): MovieDetailsResponse

    @GET("movie/{movie_id}/recommendations")
    suspend fun fetchRecommendedMovies(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): ListOfMoviesResponse
}
