package com.amalitech.rooms.book_room

import com.amalitech.core.util.UiText

data class RoomUiState(
    val bookRoomUi: BookRoomUi = BookRoomUi(),
    val error: UiText? = null,
    val isLoading: Boolean = false
)
