package com.amalitech.home

import java.time.LocalDate

data class HomeUiState(
    val bookingsForDay: List<BookingUiState> = emptyList(),
    val bookings: Map<LocalDate, List<BookingUiState>> = emptyMap()
)
