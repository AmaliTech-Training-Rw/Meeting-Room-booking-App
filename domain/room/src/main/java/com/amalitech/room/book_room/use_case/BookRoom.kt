package com.amalitech.room.book_room.use_case

import com.amalitech.core.domain.model.Booking
import com.amalitech.core.util.UiText
import kotlinx.coroutines.delay

class BookRoom {
    suspend operator fun invoke(booking: Booking): UiText? {
        delay(2000)
        return null
    }
}
