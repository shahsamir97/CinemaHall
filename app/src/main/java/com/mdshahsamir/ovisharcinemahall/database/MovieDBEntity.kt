package com.mdshahsamir.ovisharcinemahall.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieDBEntity (
    val adult: Boolean = false,
    @PrimaryKey
    val id: Int = 1,
    val originalTitle: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val posterPath: String = "",
    val releaseDate: String = "",
    val title: String = "",
    val voteAverage: Double  = 0.0,
    var isAddedToWishlist: Boolean = false
)