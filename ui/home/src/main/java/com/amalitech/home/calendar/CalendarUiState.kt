package com.amalitech.home.calendar

import java.time.LocalDate

data class CalendarUiState(
    val bookingsForDay: List<BookingUiState> = emptyList(),
    val bookings: Map<LocalDate, List<BookingUiState>> = emptyMap()
)
