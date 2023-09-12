package com.amalitech.home.data_source.remote.dto

import com.amalitech.core.domain.model.Booking
import com.amalitech.core.util.DateConverter
import com.squareup.moshi.Json

data class HomeBookingData(
    val approved: Int?,
    val declined: Int?,
    @Json(name = "end_date")
    val endDate: String?,
    val id: Int?,
    val note: String?,
    @Json(name = "organisation_id")
    val organisationId: Int?,
    val room: Room?,
    @Json(name = "room_id")
    val roomId: Int?,
    @Json(name = "start_date")
    val startDate: String?,
    @Json(name = "user_id")
    val userId: Int?
) {
    fun toBooking(): Booking {
        return Booking(
            startTime = DateConverter.stringToTime(startDate ?: ""),
            endTime = DateConverter.stringToTime(endDate ?: ""),
            roomName = room?.name ?: "",
            roomId = roomId.toString(),
            attendees = emptyList(),
            note = note ?: "",
            date = DateConverter.stringToDate(startDate ?: "")
        )
    }
}
