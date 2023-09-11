package com.amalitech.booking.use_case

import com.amalitech.booking.model.Booking
import com.amalitech.booking.repository.BookingRepository
import com.amalitech.core.util.ApiResult

class GetBookingsUseCase(
    private val repository: BookingRepository
) {

    suspend operator fun invoke(ended: Boolean = false): ApiResult<List<Booking>> {
        return repository.fetchUsersBookings(ended)
    }
}
