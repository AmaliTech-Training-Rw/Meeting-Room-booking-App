package com.amalitech.home.book_room.model

import com.amalitech.home.model.Booking

data class BookableRoom(
    val name: String,
    val description: String,
    val features: List<String>,
    val bookings: List<Booking>
)
