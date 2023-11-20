package com.mdshahsamir.ovisharcinemahall.di

import android.content.Context
import androidx.room.Room
import com.mdshahsamir.ovisharcinemahall.database.AppDatabase
import com.mdshahsamir.ovisharcinemahall.database.MovieDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    companion object {
        private const val DB_NAME = "movie_database"
    }

    @Singleton
    @Provides
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            DatabaseModule.DB_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideDao(database: AppDatabase) : MovieDao {
        return database.movieDao()
    }
}