package com.amalitech.home.book_room.use_case

import com.amalitech.core.util.UiText
import com.amalitech.home.book_room.model.Booking
import kotlinx.coroutines.delay

class BookRoom {
    suspend operator fun invoke(booking: Booking): UiText? {
        delay(2000)
        return null
    }
}
