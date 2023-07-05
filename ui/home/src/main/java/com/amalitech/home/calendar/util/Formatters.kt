package com.amalitech.home.calendar.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


fun formatDate(localDate: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy")
    return localDate.format(formatter)
}

fun formatTime(localDateTime: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("hh:mm a")
    return localDateTime.format(formatter)
}
