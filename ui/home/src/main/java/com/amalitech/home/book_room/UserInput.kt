package com.amalitech.home.book_room

import java.time.LocalDate
import java.time.LocalTime

data class UserInput(
    val date: LocalDate? = null,
    val startTime: LocalTime? = null,
    val endTime: LocalTime? = null,
    val attendees: List<String> = listOf(),
    val note: String = "",
    val attendee: String = ""
)
