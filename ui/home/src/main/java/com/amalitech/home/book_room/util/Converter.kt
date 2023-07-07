package com.amalitech.home.book_room.util

import com.amalitech.home.book_room.BookingUi
import com.amalitech.home.book_room.RoomUi
import com.amalitech.home.book_room.model.BookableRoom
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
        },
        imgUrl = imgUrl
    )
}

fun formatDate(localDate: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return localDate.format(formatter)
}
