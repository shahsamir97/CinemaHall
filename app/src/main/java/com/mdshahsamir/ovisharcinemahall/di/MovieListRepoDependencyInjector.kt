package com.mdshahsamir.ovisharcinemahall.di

import android.content.Context
import com.mdshahsamir.ovisharcinemahall.database.AppDatabase
import com.mdshahsamir.ovisharcinemahall.network.ApiServiceGenerator
import com.mdshahsamir.ovisharcinemahall.network.MovieAPIService
import com.mdshahsamir.ovisharcinemahall.ui.movies.MovieListRepository
import com.mdshahsamir.ovisharcinemahall.ui.movies.MovieListRepositoryImpl

class MovieListRepoDependencyInjector(private val context: Context) {

    fun getMovieListRepository(): MovieListRepository {
        return MovieListRepositoryImpl(getMovieApiService(), getMovieDao())
    }

    private fun getMovieApiService() =
        ApiServiceGenerator.createService(MovieAPIService::class.java)

    private fun getMovieDao() = AppDatabase.getDatabase(context).movieDao()
}