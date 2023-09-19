package com.amalitech.rooms.model

import java.time.LocalTime

data class Time(
    val time: LocalTime,
    val isAvailable: Boolean
)
