package com.amalitech.home.model

import java.time.LocalDateTime

data class Booking(
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val roomName: String,
)
