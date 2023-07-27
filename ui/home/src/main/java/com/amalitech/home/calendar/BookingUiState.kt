package com.amalitech.home.calendar

import java.time.LocalDate
import java.time.LocalTime

data class BookingUiState(
    val startTime: LocalTime,
    val endTime: LocalTime,
    val roomName: String,
    val date: LocalDate
)
