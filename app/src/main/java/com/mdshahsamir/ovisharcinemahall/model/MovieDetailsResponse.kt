package com.mdshahsamir.ovisharcinemahall.model

import android.icu.text.SimpleDateFormat
import com.google.gson.annotations.SerializedName
import com.mdshahsamir.ovisharcinemahall.util.API_DATE_FORMAT
import java.util.Locale

data class MovieDetailsResponse(
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("belongs_to_collection")
    val belongsToCollection: BelongsToCollection,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    @SerializedName("imdb_id")
    val imdbId: String,
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
    val revenue: Int,
    val runtime: Int,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
) {
    fun getReleaseYear(): String {
        val inputFormat = SimpleDateFormat(API_DATE_FORMAT, Locale.US)
        val outputFormat = SimpleDateFormat("yyyy", Locale.US)

        val date = inputFormat.parse(releaseDate)
        return outputFormat.format(date!!)
    }
}