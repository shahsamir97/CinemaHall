package com.mdshahsamir.ovisharcinemahall.util

import android.icu.text.SimpleDateFormat
import androidx.recyclerview.widget.DiffUtil
import com.mdshahsamir.ovisharcinemahall.model.Movie
import java.text.ParseException
import java.util.Locale

fun getDottedText(list: List<String>): String {
    var dottedString = ""

    for ((i, item) in list.withIndex()) {
        dottedString += if (i == list.size - 1) item else ("$item • ")
    }

    return dottedString
}

fun formatDateToDDMMMMYYYY(inputDate: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val outputFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.US)

        val date = inputFormat.parse(inputDate)
        outputFormat.format(date!!)
    } catch (e: ParseException) {
        e.printStackTrace()
        ""
    }
}

fun getYearFromApiDate(inputDate: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    val outputFormat = SimpleDateFormat("yyyy", Locale.US)

    val date = inputFormat.parse(inputDate)
    return outputFormat.format(date!!)
}

object MovieDiffUtil : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}
