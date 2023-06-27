package com.amalitech.home

import java.time.LocalDateTime

data class BookingUiState(
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val roomName: String
)
