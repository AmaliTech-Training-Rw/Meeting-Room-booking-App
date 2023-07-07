package com.amalitech.home.book_room

data class SlotSelectionManager(
    val availableStartTimes: List<TimeUi> = emptyList(),
    val availableEndTimes: List<TimeUi> = emptyList(),
    val canShowStartTimes: Boolean = false,
    val canShowEndTimes: Boolean = false
)
