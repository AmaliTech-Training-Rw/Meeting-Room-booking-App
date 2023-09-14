package com.amalitech.core.util

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object DateConverter {
    private val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    private val dateStringFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val timeStringFormat = DateTimeFormatter.ofPattern("HH:mm:ss")
    fun stringToDate(date: String): LocalDate {
        return LocalDate.parse(date, dateFormat)
    }

    fun stringToTime(time: String): LocalTime {
        return LocalTime.parse(time, dateFormat)
    }

    fun dateToString(date: LocalDate): String {
        return date.format(dateStringFormat)
    }

    fun timeToString(time: LocalTime): String {
        return time.format(timeStringFormat)
    }
}
