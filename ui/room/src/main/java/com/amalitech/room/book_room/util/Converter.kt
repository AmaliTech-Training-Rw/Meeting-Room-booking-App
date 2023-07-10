package com.amalitech.room.book_room.util

import com.example.room.book_room.RoomUiState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun com.amalitech.room.book_room.model.Room.toBookRoomUi(): RoomUiState {
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
