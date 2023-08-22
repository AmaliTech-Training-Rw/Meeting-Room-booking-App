package com.amalitech.booking.requests.detail

import com.amalitech.booking.model.BookingRequestDetail
import com.amalitech.core.util.UiText

data class BookingRequestDetailUiState(
    val bookingRequestDetail: BookingRequestDetail? = null,
    val isLoading: Boolean = true,
    val error: UiText? = null
)
