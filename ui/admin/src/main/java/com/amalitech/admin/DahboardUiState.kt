package com.amalitech.admin

import com.amalitech.core.util.UiText

data class DashboardUiState(
    val rooms: List<RoomsBookedTime> = emptyList(),
    val bookingsCount: Int = 0,
    val roomsCount: Int = 0,
    val usersCount: Int = 0,
    val loading: Boolean = false,
    val error: UiText? = null
)
