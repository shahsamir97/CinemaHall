package com.mdshahsamir.ovisharcinemahall.di

import com.mdshahsamir.ovisharcinemahall.network.ApiServiceGenerator
import com.mdshahsamir.ovisharcinemahall.network.MovieAPIService
import com.mdshahsamir.ovisharcinemahall.ui.movies.MovieListRepository
import com.mdshahsamir.ovisharcinemahall.ui.movies.MovieListRepositoryImpl

object MovieListRepoDependencyInjector {

    fun getMovieListRepository(): MovieListRepository {
        return MovieListRepositoryImpl(getMovieApiService())
    }

    private fun getMovieApiService() = ApiServiceGenerator.createService(MovieAPIService::class.java)
}