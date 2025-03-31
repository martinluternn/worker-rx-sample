package com.example.util.ext

import com.example.util.Const
import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale

fun String.toDateString(
    toFormat: String,
    fromFormat: String = Const.RFC3339_DATE_TIME_FORMAT,
    timeZone: TimeZone = TimeZone.getDefault()
): String {
    return if (this.isNotEmpty()) {
        try {
            val formatter = SimpleDateFormat(fromFormat, Locale("id", "ID"))
            formatter.timeZone = timeZone
            formatter.parse(this).formatToTimeZone(toFormat, TimeZone.getDefault())
        } catch (e: Exception) {
            e.printStackTrace()
            this
        }
    } else {
        this
    }
}

fun Date.formatToTimeZone(
    dateFormat: String = Const.RFC3339_DATE_TIME_FORMAT,
    timeZone: TimeZone = TimeZone.getTimeZone("UTC")
): String {
    return try {
        val formatter = SimpleDateFormat(dateFormat, Locale("id", "ID"))
        formatter.timeZone = timeZone
        return formatter.format(this)
    } catch (e: Exception) {
        e.printStackTrace()
        this.toString()
    }
}

fun startingDate(): String {
    val date = Calendar.getInstance()
    date.add(Calendar.DATE, -1)
    return date.time.formatToTimeZone(Const.APP_DB_DATE_FORMAT_START)
}

fun endedDate(): String {
    val date = Calendar.getInstance()
    date.add(Calendar.DATE, -1)
    return date.time.formatToTimeZone(Const.APP_DB_DATE_FORMAT_END)
}