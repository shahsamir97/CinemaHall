package com.mdshahsamir.ovisharcinemahall.di

import com.mdshahsamir.ovisharcinemahall.database.MovieDao
import com.mdshahsamir.ovisharcinemahall.network.MovieAPIService
import com.mdshahsamir.ovisharcinemahall.ui.dashboard.DashboardRepository
import com.mdshahsamir.ovisharcinemahall.ui.dashboard.DashboardRepositoryIml
import com.mdshahsamir.ovisharcinemahall.ui.moviedetails.MovieDetailsRepository
import com.mdshahsamir.ovisharcinemahall.ui.moviedetails.MovieDetailsRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideDashboardRepository(movieAPIService: MovieAPIService, movieDao: MovieDao): DashboardRepository {
        return DashboardRepositoryIml(movieAPIService, movieDao)
    }

    @Provides
    fun provideMovieDetailsRepository(movieAPIService: MovieAPIService): MovieDetailsRepository {
        return MovieDetailsRepositoryImpl(movieAPIService)
    }
}