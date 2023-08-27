package com.mdshahsamir.ovisharcinemahall.model.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieDTO (
        val adult: Boolean = false,
        @PrimaryKey
        val id: Int = 1,
        val originalTitle: String = "",
        val overview: String = "",
        val posterPath: String = "",
        val releaseDate: String = "",
        val title: String = "",
        val voteAverage: Double  = 0.0,
        var isAddedToWishlist: Boolean = false
)