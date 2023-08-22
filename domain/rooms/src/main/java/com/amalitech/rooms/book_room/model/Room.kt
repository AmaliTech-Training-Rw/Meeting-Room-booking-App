package com.amalitech.rooms.book_room.model

import com.amalitech.core.domain.model.Booking

data class Room(
    val name: String,
    val description: String,
    val features: List<String>,
    val bookings: List<Booking>,
    val imgUrl: String,
    val capacity: Int = 0
)
