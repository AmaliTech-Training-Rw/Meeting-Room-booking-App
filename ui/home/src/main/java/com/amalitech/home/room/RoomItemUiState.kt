package com.amalitech.home.room

data class RoomItemUiState(
    val room_name: String,
    val number_of_people: Int,
    val description: String,
    val imageRes : String,
)

data class RoomUiState(
    val loading: Boolean = true,
    val rooms: List<RoomItemUiState> = emptyList()
)
