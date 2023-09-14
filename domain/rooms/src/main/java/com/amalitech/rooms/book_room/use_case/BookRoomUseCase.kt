package com.amalitech.rooms.book_room.use_case

import com.amalitech.core.domain.model.Booking
import com.amalitech.core.util.UiText
import com.amalitech.rooms.repository.RoomRepository

class BookRoomUseCase(
    private val repository: RoomRepository
) {
    suspend operator fun invoke(booking: Booking): UiText? {
        return repository.bookRoom(booking)
    }
}
