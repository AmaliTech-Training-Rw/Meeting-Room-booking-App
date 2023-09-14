package com.amalitech.rooms.book_room

import com.amalitech.core.domain.model.Booking

data class BookRoomUi(
    val name: String = "",
    val features: List<String> = emptyList(),
    val bookings: List<Booking> = emptyList(),
    val canNavigate: Boolean = false,
    val imgUrl: String = "",
    val capacity: Int = 0
)
