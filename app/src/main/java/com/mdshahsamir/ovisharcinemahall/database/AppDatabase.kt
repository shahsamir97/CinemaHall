package com.mdshahsamir.ovisharcinemahall.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mdshahsamir.ovisharcinemahall.model.Movie

@Database(entities = [MovieDBEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao() : MovieDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null
        private const val DB_NAME = "movie_database"

        fun getDatabase(context: Context) : AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}