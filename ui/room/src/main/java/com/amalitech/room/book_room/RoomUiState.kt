package com.amalitech.room.book_room

import com.amalitech.core.domain.model.Booking

data class RoomUiState(
    val name: String = "",
    val description: String = "",
    val features: List<String> = emptyList(),
    val bookings: List<Booking> = emptyList(),
    val canNavigate: Boolean = false,
    val imgUrl: String = ""
)
