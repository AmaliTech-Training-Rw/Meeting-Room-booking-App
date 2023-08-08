package com.amalitech.rooms.book_room.util

import com.amalitech.rooms.book_room.BookRoomUi
import com.amalitech.rooms.book_room.model.Room
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun Room.toBookRoomUi(): BookRoomUi {
    return BookRoomUi(
        name,
        description,
        features,
        bookings,
        imgUrl = imgUrl,
        capacity = capacity
    )
}

fun formatDate(localDate: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return localDate.format(formatter)
}
