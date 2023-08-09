package com.amalitech.booking.model

import java.time.LocalDate
import java.time.LocalTime

data class Booking(
    val roomName: String,
    val date: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val imgUrl: String
)
