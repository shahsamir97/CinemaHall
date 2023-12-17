package com.mdshahsamir.ovisharcinemahall.di

import android.content.Context
import androidx.room.Room
import com.mdshahsamir.ovisharcinemahall.database.AppDatabase
import com.mdshahsamir.ovisharcinemahall.database.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideCountryDB(@ApplicationContext context: Context): MovieDao {
        return Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DB_NAME).build().movieDao()
    }
}