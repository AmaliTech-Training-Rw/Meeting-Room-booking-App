package com.amalitech.home.book_room

import java.time.LocalDate
import java.time.LocalTime

data class BookingUi(
    val startTime: LocalTime,
    val endTime: LocalTime,
    val date: LocalDate
)
