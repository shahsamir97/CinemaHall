package com.mdshahsamir.ovisharcinemahall.ui.moviedetails

import com.mdshahsamir.ovisharcinemahall.model.Movie
import com.mdshahsamir.ovisharcinemahall.model.MovieDetails
import com.mdshahsamir.ovisharcinemahall.network.MovieAPIService
import com.mdshahsamir.ovisharcinemahall.util.toMovie
import com.mdshahsamir.ovisharcinemahall.util.toMovieDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface MovieDetailsRepository {

    fun fetchMovieDetails(movieId: Int): Flow<Pair<MovieDetails, List<Movie>>>
}

class MovieDetailsRepositoryImpl @Inject constructor(private val apiService: MovieAPIService) : MovieDetailsRepository {

    override fun fetchMovieDetails(movieId: Int): Flow<Pair<MovieDetails, List<Movie>>> =
        flow {
            val movieDetails = apiService.fetchMovieDetails(movieId).toMovieDetails()
            val recommendedMovies = apiService.fetchRecommendedMovies(movieId).results!!.map { it.toMovie() }
            emit(Pair(movieDetails, recommendedMovies))
        }
}
