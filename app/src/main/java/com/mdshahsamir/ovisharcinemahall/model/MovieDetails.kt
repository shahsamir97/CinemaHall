package com.mdshahsamir.ovisharcinemahall.model

import android.icu.text.SimpleDateFormat
import com.mdshahsamir.ovisharcinemahall.util.API_DATE_FORMAT
import java.text.ParseException
import java.util.Locale

data class MovieDetails (
    val adult: Boolean,
    val backdropPath: String,
    val genres: List<Genre>,
    val id: Int,
    val originalTitle: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double,
) {
    fun getReleaseYear(): String {
        val inputFormat = SimpleDateFormat(API_DATE_FORMAT, Locale.US)
        val outputFormat = SimpleDateFormat("yyyy", Locale.US)

        return try {
            val date = inputFormat.parse(releaseDate)
            outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            "N/A"
        }
    }
}
