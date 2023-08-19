package com.mdshahsamir.ovisharcinemahall.ui.moviedetails

import com.mdshahsamir.ovisharcinemahall.model.MovieDetailsResponse
import com.mdshahsamir.ovisharcinemahall.network.MovieAPIService

interface MovieDetailsRepository {

    suspend fun fetchMovieDetails(movieId: Int): MovieDetailsResponse

    fun fetchRecommendedMovies()
}

class MovieDetailsRepositoryImpl(private val apiService: MovieAPIService) : MovieDetailsRepository {

    override suspend fun fetchMovieDetails(movieId: Int): MovieDetailsResponse {
       return apiService.fetchMovieDetails(movieId)
    }

    override fun fetchRecommendedMovies() {}

}
