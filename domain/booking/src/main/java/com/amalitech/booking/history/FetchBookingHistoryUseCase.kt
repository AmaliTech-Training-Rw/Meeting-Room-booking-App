package com.amalitech.booking.history

import com.amalitech.booking.model.Booking
import com.amalitech.booking.repository.BookingRepository
import com.amalitech.core.util.ApiResult

class FetchBookingHistoryUseCase(
    private val repository: BookingRepository
) {
    suspend operator fun invoke(): ApiResult<List<Booking>> {
        return repository.fetchBookingHistory()
    }
}
