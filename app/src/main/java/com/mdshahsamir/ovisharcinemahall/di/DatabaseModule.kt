package com.mdshahsamir.ovisharcinemahall.di

import android.content.Context
import androidx.room.Room
import com.mdshahsamir.ovisharcinemahall.database.AppDatabase
import com.mdshahsamir.ovisharcinemahall.database.MovieDao
import com.mdshahsamir.ovisharcinemahall.network.MovieAPIService
import com.mdshahsamir.ovisharcinemahall.ui.dashboard.DashboardRepository
import com.mdshahsamir.ovisharcinemahall.ui.dashboard.DashboardRepositoryIml
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideCountryDB(context: Context): MovieDao {
        return Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DB_NAME).build().movieDao()
    }

    @Provides
    @Singleton
    fun provideDashboardRepository(movieAPIService: MovieAPIService, movieDao: MovieDao): DashboardRepository {
        return DashboardRepositoryIml(movieAPIService, movieDao)
    }
}