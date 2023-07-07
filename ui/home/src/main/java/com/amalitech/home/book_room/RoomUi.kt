package com.amalitech.home.book_room

data class RoomUi(
    val name: String = "",
    val description: String = "",
    val features: List<String> = emptyList(),
    val bookings: List<BookingUi> = emptyList(),
    val canNavigate: Boolean = false,
    val imgUrl: String = ""
)
