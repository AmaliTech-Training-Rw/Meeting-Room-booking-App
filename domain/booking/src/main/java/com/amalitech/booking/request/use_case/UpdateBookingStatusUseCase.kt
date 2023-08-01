package com.amalitech.booking.request.use_case

import com.amalitech.booking.model.Booking
import com.amalitech.core.util.UiText
import kotlinx.coroutines.delay

class UpdateBookingStatusUseCase {
    suspend operator fun invoke(isApproved: Boolean, booking: Booking): UiText? {
        delay(200)
        if (isApproved)
            return null
        return UiText.DynamicString("Cannot decline booking")
    }
}
