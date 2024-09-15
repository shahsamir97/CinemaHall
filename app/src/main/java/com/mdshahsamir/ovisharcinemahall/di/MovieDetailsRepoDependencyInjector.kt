package com.mdshahsamir.ovisharcinemahall.di

import com.mdshahsamir.ovisharcinemahall.network.ApiServiceGenerator
import com.mdshahsamir.ovisharcinemahall.network.MovieAPIService
import com.mdshahsamir.ovisharcinemahall.ui.moviedetails.MovieDetailsRepository
import com.mdshahsamir.ovisharcinemahall.ui.moviedetails.MovieDetailsRepositoryImpl

object MovieDetailsRepoDependencyInjector {

    fun getMovieListRepository(): MovieDetailsRepository {
        return MovieDetailsRepositoryImpl(getMovieApiService())
    }

    private fun getMovieApiService() = ApiServiceGenerator.createService(MovieAPIService::class.java)
}