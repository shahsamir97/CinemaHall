package com.mdshahsamir.ovisharcinemahall.ui.moviedetails

import com.mdshahsamir.ovisharcinemahall.model.Movie
import com.mdshahsamir.ovisharcinemahall.model.MovieDetailsResponse
import com.mdshahsamir.ovisharcinemahall.network.MovieAPIService

interface MovieDetailsRepository {

    suspend fun fetchMovieDetails(movieId: Int): Result<MovieDetailsResponse>

    suspend fun fetchRecommendedMovies(movieId: Int): Result<List<Movie>>

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

    override suspend fun fetchRecommendedMovies(movieId: Int): Result<List<Movie>> {
        return  try {
            val response = apiService.fetchRecommendedMovies(movieId)
            Result.success(response.results)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override fun fetchRecommendedMovies() {

    }

}
