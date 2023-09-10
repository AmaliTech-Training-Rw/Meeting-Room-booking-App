package com.amalitech.booking.request.detail

import com.amalitech.booking.model.BookingRequestDetail
import com.amalitech.booking.repository.BookingRepository
import com.amalitech.core.util.ApiResult

class GetBookingRequestDetailUseCase(
    private val repository: BookingRepository
) {
    suspend operator fun invoke(id: String): ApiResult<BookingRequestDetail> {
        return repository.getBooking(id)
    }
}
