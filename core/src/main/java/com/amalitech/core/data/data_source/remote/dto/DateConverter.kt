package com.amalitech.core.data.data_source.remote.dto

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object DateConverter {
    private val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    fun stringToDate(date: String): LocalDate {
        return LocalDate.parse(date, dateFormat)
    }

    fun stringToTime(time: String): LocalTime {
        return LocalTime.parse(time, dateFormat)
    }
}
