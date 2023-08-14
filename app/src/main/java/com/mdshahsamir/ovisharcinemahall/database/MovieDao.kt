package com.mdshahsamir.ovisharcinemahall.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mdshahsamir.ovisharcinemahall.model.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovie(movie: Movie)

    @Query("SELECT * FROM movie")
    fun getAllMovie(): List<Movie>
}