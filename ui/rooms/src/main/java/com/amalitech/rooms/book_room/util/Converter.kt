package com.amalitech.rooms.book_room.util

import com.amalitech.core.data.model.Room
import com.amalitech.rooms.book_room.BookRoomUi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun Room.toBookRoomUi(): BookRoomUi {
    return BookRoomUi(
        roomName,
        roomFeatures,
        imgUrl = this.imageUrl.randomOrNull() ?: "",
        capacity = numberOfPeople,
    )
}

fun formatDate(localDate: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return localDate.format(formatter)
}
