package com.mdshahsamir.ovisharcinemahall.ui.movies

import com.mdshahsamir.ovisharcinemahall.model.Movie
import com.mdshahsamir.ovisharcinemahall.network.MovieAPIService

interface MovieListRepository {

    suspend fun fetchTopRatedMovies(): List<Movie>
}

class MovieListRepositoryImpl(private val movieAPIService: MovieAPIService): MovieListRepository {

    override suspend fun fetchTopRatedMovies(): List<Movie> {
            val response = movieAPIService.fetchTopRatedMovies()
            return response.results
    }
}
