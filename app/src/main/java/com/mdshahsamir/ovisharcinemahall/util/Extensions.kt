package com.mdshahsamir.ovisharcinemahall.util

import android.icu.text.SimpleDateFormat
import java.text.ParseException
import java.util.Locale

fun String.toDisplayableDateFormat(inputDateFormat: String): String {
    return try {
        val inputFormat = SimpleDateFormat(inputDateFormat, Locale.US)
        val outputFormat = SimpleDateFormat(DISPLAY_DATE_FORMAT, Locale.US)
        val date = inputFormat.parse(this)

        outputFormat.format(date!!)
    } catch (e: ParseException) {
        e.printStackTrace()
        ""
    }
}
