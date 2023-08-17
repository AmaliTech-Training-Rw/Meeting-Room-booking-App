package com.amalitech.booking.model

import com.amalitech.core.data.model.Room

data class BookingRequestDetail(
    val booking: Booking,
    val room: Room
)
