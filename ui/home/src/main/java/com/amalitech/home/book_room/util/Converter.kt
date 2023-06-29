package com.amalitech.home.book_room.util

import com.amalitech.home.book_room.BookingUi
import com.amalitech.home.book_room.RoomUi
import com.amalitech.home.book_room.model.BookableRoom

fun BookableRoom.toBookRoomUi(): RoomUi {
    return RoomUi(
        name,
        description,
        features,
        bookings.map {
            BookingUi(
                it.startTime.toLocalTime(),
                it.endTime.toLocalTime(),
                it.startTime.toLocalDate()
            )
        }
    )
}
