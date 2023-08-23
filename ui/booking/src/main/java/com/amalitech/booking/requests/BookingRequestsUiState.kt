package com.amalitech.booking.requests

import com.amalitech.booking.model.Booking
import com.amalitech.core.util.UiText

data class BookingRequestsUiState(
    val bookings: List<Booking> = emptyList(),
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val searchQuery: String = "",
)
