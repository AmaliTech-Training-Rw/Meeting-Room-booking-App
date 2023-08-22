package com.amalitech.core_ui.util

import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


fun formatDate(localDate: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy")
    return localDate.format(formatter)
}

fun formatTime(localDateTime: LocalTime): String {
    val formatter = DateTimeFormatter.ofPattern("hh:mm a")
    return localDateTime.format(formatter)
}

fun formatDate1(date: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return date.format(formatter)
}

fun longToLocalDate(utcTimeMillis: Long): LocalDate{
    return Instant.ofEpochMilli(utcTimeMillis)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}
