package com.amalitech.dashboard.data_source.remote.dto

import com.amalitech.dashboard.model.DashboardData
import com.amalitech.dashboard.model.RoomGraphInfo

data class DashboardDto(
    val users: Int?,
    val rooms: DashboardRoom?,
    val bookings: Int?
) {
    fun toDomainData(): DashboardData {
        return DashboardData(
            users = users ?: 0,
            bookings = bookings ?: 0,
            rooms = rooms?.data?.map { RoomGraphInfo(it.name, it.countBookings) } ?: emptyList()
        )
    }
}
