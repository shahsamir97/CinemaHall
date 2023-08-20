package com.mdshahsamir.ovisharcinemahall.ui.moviedetails

import com.mdshahsamir.ovisharcinemahall.model.MovieDetailsResponse
import com.mdshahsamir.ovisharcinemahall.network.MovieAPIService

interface MovieDetailsRepository {

    suspend fun fetchMovieDetails(movieId: Int): Result<MovieDetailsResponse>

    fun fetchRecommendedMovies()
}

class MovieDetailsRepositoryImpl(private val apiService: MovieAPIService) : MovieDetailsRepository {

    override suspend fun fetchMovieDetails(movieId: Int): Result<MovieDetailsResponse> {
        return try {
            val movieDetails = apiService.fetchMovieDetails(movieId)
            Result.success(movieDetails)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override fun fetchRecommendedMovies() {

    }

}
