package com.amalitech.booking.repository

import com.amalitech.booking.model.Booking
import com.amalitech.core.util.ApiResult
import com.amalitech.core.util.UiText

interface BookingRepository {
    suspend fun fetchBookingRequests(): ApiResult<List<Booking>>

    suspend fun approveBooking(id: String): UiText?

    suspend fun declineBooking(id: String): UiText?
}
