package com.amalitech.core.domain.model

import java.time.LocalDate
import java.time.LocalTime

data class Booking(
    val startTime: LocalTime,
    val endTime: LocalTime,
    val roomName: String = "",
    val roomId: String,
    val attendees: List<String>,
    val note: String,
    val date: LocalDate
)
