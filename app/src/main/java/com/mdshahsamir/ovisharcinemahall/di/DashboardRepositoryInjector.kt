package com.mdshahsamir.ovisharcinemahall.di

import android.content.Context
import com.mdshahsamir.ovisharcinemahall.database.AppDatabase
import com.mdshahsamir.ovisharcinemahall.network.ApiServiceGenerator
import com.mdshahsamir.ovisharcinemahall.network.MovieAPIService
import com.mdshahsamir.ovisharcinemahall.ui.dashboard.SharedRepository
import com.mdshahsamir.ovisharcinemahall.ui.dashboard.SharedRepositoryIml

class DashboardRepositoryInjector(private val context: Context) {

    fun getSharedRepository(): SharedRepository {
        return SharedRepositoryIml(getMovieApiService(), getMovieDao())
    }

    private fun getMovieApiService() =
        ApiServiceGenerator.createService(MovieAPIService::class.java)

    private fun getMovieDao() = AppDatabase.getDatabase(context).movieDao()
}