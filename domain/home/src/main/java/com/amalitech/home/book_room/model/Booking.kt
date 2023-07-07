package com.amalitech.home.book_room.model

data class Booking(
    val roomId: String,
    val attendees: List<String>,
    val note: String
)
