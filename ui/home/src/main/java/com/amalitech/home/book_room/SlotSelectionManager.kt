package com.amalitech.home.book_room

import java.time.LocalTime

data class SlotSelectionManager(
    val availableStartTimes: List<LocalTime> = emptyList(),
    val availableEndTimes: List<LocalTime> = emptyList(),
    val canShowStartTimes: Boolean = false,
    val canShowEndTimes: Boolean = false
)
