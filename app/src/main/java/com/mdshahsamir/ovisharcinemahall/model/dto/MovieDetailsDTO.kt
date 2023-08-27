package com.mdshahsamir.ovisharcinemahall.model.dto

import android.icu.text.SimpleDateFormat
import com.mdshahsamir.ovisharcinemahall.model.Genre
import com.mdshahsamir.ovisharcinemahall.util.API_DATE_FORMAT
import java.text.ParseException
import java.util.Locale

data class MovieDetailsDTO(
    val adult: Boolean = false,
    val backdropPath: String = "",
    val genres: List<Genre> = listOf(),
    val id: Int = 1,
    val originalTitle: String = "",
    val overview: String = "",
    val posterPath: String = "",
    val releaseDate: String = "",
    val voteAverage: Double = 0.0,
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
