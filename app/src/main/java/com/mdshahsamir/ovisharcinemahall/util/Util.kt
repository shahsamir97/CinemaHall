package com.mdshahsamir.ovisharcinemahall.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import com.mdshahsamir.ovisharcinemahall.model.Movie

const val API_DATE_FORMAT = "yyyy-MM-dd"
const val DISPLAY_DATE_FORMAT = "MMMM dd, yyyy"

fun getDottedText(list: List<String>): String {
    var dottedString = ""

    for ((i, item) in list.withIndex()) {
        dottedString += if (i == list.size - 1) item else ("$item • ")
    }

    return dottedString
}

object MovieDiffUtil : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}

private fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork
    val networkCapabilities = connectivityManager.getNetworkCapabilities(network)

    return networkCapabilities?.run {
        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    } ?: false
}

fun runIfInternetAvailable(context: Context, noInternetMessage: String? = null, codeBlock:() -> Unit) {
    if (isInternetAvailable(context)) {
        codeBlock()
    } else {
        Toast.makeText(context, noInternetMessage ?: "You're not connected to internet", Toast.LENGTH_SHORT).show()
    }
}

fun getColorFromAttribute(colorRes: Int, context: Context): Int {
    val attrs = intArrayOf(colorRes)
    val typedArray = context.obtainStyledAttributes(attrs)
    val color = typedArray.getColor(0, 0) // Default color value (0) in case the attribute is not found
    typedArray.recycle()

    return color
}
