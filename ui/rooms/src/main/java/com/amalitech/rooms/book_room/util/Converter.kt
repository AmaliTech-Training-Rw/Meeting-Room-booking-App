package com.amalitech.rooms.book_room.util

import com.amalitech.rooms.book_room.RoomUiState
import com.amalitech.rooms.book_room.model.Room
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun Room.toBookRoomUi(): RoomUiState {
    return RoomUiState(
        name,
        description,
        features,
        bookings,
        imgUrl = imgUrl
    )
}

fun formatDate(localDate: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return localDate.format(formatter)
}
