package com.amalitech.dashboard.model

data class DashboardData(
    val users: Int,
    val bookings: Int,
    val rooms: List<RoomGraphInfo>
)
