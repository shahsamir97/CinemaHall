package com.mdshahsamir.ovisharcinemahall.model

import com.google.gson.annotations.SerializedName

data class Movie(
    val adult: Boolean,
    @SerializedName("genre_ids")
    val listOfGenreId: List<Int>,
    val id: Int,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int,
    var isAddedToWishlist: Boolean = false
)
