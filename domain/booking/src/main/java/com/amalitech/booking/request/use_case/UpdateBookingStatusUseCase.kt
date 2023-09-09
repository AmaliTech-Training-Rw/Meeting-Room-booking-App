package com.amalitech.booking.request.use_case

import com.amalitech.booking.model.Booking
import com.amalitech.booking.repository.BookingRepository
import com.amalitech.core.util.UiText

class UpdateBookingStatusUseCase(
    private val repository: BookingRepository
) {
    suspend operator fun invoke(isApproved: Boolean, booking: Booking): UiText? {
        if (isApproved)
            return repository.approveBooking(booking.id)
        return repository.declineBooking(booking.id)
    }
}
