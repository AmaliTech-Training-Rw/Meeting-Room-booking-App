package com.amalitech.rooms

import com.amalitech.core.data.model.Room
import com.amalitech.core.util.UiText

data class RoomListUiState(
    val rooms: List<Room> = emptyList(),
    val loading: Boolean = false,
    val error: UiText? = null,
    val roomsCopy: List<Room> = emptyList(),
    val searchQuery: String = ""
)
