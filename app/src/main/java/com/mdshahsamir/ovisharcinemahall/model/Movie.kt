package com.mdshahsamir.ovisharcinemahall.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie (
        val adult: Boolean? = false,
        @PrimaryKey
        val id: Int,
        val originalTitle: String,
        val overview: String,
        val posterPath: String,
        val releaseDate: String,
        val title: String,
        val voteAverage: Double,
        var isAddedToWishlist: Boolean = false
)
