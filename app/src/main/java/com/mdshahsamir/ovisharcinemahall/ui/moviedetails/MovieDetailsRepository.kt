package com.mdshahsamir.ovisharcinemahall.ui.moviedetails

import com.mdshahsamir.ovisharcinemahall.model.Movie
import com.mdshahsamir.ovisharcinemahall.model.MovieDetailsResponse
import com.mdshahsamir.ovisharcinemahall.network.MovieAPIService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface MovieDetailsRepository {

    fun fetchMovieDetails(movieId: Int): Flow<Pair<MovieDetailsResponse, List<Movie>>>
}

class MovieDetailsRepositoryImpl(private val apiService: MovieAPIService) : MovieDetailsRepository {

    override fun fetchMovieDetails(movieId: Int): Flow<Pair<MovieDetailsResponse, List<Movie>>> =
        flow {
            val movieDetails = apiService.fetchMovieDetails(movieId)
            val recommendedMovies = apiService.fetchRecommendedMovies(movieId).results
            emit(Pair(movieDetails, recommendedMovies))
        }
}

