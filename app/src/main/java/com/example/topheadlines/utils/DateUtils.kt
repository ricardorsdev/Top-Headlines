package com.example.topheadlines.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    private const val apiDateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

    fun convertStringDateToTime(stringDate: String): Long {
        val sdf = SimpleDateFormat(apiDateFormat, Locale.getDefault())
        return sdf.parse(stringDate)?.time ?: 0L
    }
}