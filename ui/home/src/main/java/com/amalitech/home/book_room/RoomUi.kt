package com.amalitech.home.book_room

data class RoomUi(
    val name: String,
    val description: String,
    val features: List<String>,
    val bookings: List<BookingUi>
)
