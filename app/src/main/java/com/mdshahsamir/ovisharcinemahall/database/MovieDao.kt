package com.mdshahsamir.ovisharcinemahall.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mdshahsamir.ovisharcinemahall.model.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovie(movie: MovieDBEntity)

    @Delete
    fun deleteMovie(movie: MovieDBEntity)

    @Query("SELECT * FROM moviedbentity")
    fun getAllMovie(): LiveData<List<MovieDBEntity>>
}