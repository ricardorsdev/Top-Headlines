package com.example.topheadlines.utils

import com.example.topheadlines.BuildConfig
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    private const val apiDateFormat = BuildConfig.DATE_PATTERN

    fun convertStringDateToTime(stringDate: String?): Long {
        stringDate?.let {
            try {
                val sdf = SimpleDateFormat(apiDateFormat, Locale.getDefault())
                return sdf.parse(it)?.time ?: 0L
            } catch (e: Exception) {
                e.printStackTrace()
                return 0L
            }
        }
        return 0L
    }
}