package com.amalitech.core_ui.util

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


fun formatDate(localDate: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy")
    return localDate.format(formatter)
}

fun formatTime(localDateTime: LocalTime): String {
    val formatter = DateTimeFormatter.ofPattern("hh:mm a")
    return localDateTime.format(formatter)
}

fun formatLocalTime(time: LocalTime): String {
    val formatter = DateTimeFormatter.ofPattern("H:mm")
    return time.format(formatter)
}
