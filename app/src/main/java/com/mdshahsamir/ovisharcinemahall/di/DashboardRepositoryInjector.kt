package com.mdshahsamir.ovisharcinemahall.di

import android.content.Context
import com.mdshahsamir.ovisharcinemahall.database.AppDatabase
import com.mdshahsamir.ovisharcinemahall.network.ApiServiceGenerator
import com.mdshahsamir.ovisharcinemahall.network.MovieAPIService
import com.mdshahsamir.ovisharcinemahall.ui.dashboard.DashboardRepository
import com.mdshahsamir.ovisharcinemahall.ui.dashboard.DashboardRepositoryIml

class DashboardRepositoryInjector(private val context: Context) {

    fun getSharedRepository(): DashboardRepository {
        return DashboardRepositoryIml(getMovieApiService(), getMovieDao())
    }

    private fun getMovieApiService() =
        ApiServiceGenerator.createService(MovieAPIService::class.java)

    private fun getMovieDao() = AppDatabase.getDatabase(context).movieDao()
}