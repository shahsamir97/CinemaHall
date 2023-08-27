package com.mdshahsamir.ovisharcinemahall.util

import androidx.recyclerview.widget.DiffUtil
import com.mdshahsamir.ovisharcinemahall.model.Movie
import com.mdshahsamir.ovisharcinemahall.model.dto.MovieDTO

const val API_DATE_FORMAT = "yyyy-MM-dd"
const val DISPLAY_DATE_FORMAT = "MMMM dd, yyyy"

fun getDottedText(list: List<String>): String {
    var dottedString = ""

    for ((i, item) in list.withIndex()) {
        dottedString += if (i == list.size - 1) item else ("$item • ")
    }

    return dottedString
}

object MovieDiffUtil : DiffUtil.ItemCallback<MovieDTO>() {
    override fun areItemsTheSame(oldItem: MovieDTO, newItem: MovieDTO): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieDTO, newItem: MovieDTO): Boolean {
        return oldItem == newItem
    }
}
