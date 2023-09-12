package com.amalitech.booking.request.use_case

import com.amalitech.booking.model.Booking
import com.amalitech.booking.repository.BookingRepository
import com.amalitech.core.util.ApiResult

class FetchBookingsUseCase(
    private val repository: BookingRepository
) {

    suspend operator fun invoke(): ApiResult<List<Booking>> {
        return repository.fetchBookingRequests()
    }
}
