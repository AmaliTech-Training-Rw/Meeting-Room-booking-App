package com.amalitech.booking.request.use_case

data class BookingRequestsUseCaseWrapper(
    val fetchBookingsUseCase: FetchBookingsUseCase,
    val updateBookingStatusUseCase: UpdateBookingStatusUseCase
)
