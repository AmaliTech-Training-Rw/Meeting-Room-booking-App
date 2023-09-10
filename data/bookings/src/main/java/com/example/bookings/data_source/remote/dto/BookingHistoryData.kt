package com.example.bookings.data_source.remote.dto

import com.amalitech.booking.model.Booking
import com.squareup.moshi.Json

data class BookingHistoryData(
    val approved: Int?,
    val declined: Int?,
    @Json(name = "end_date")
    val endDate: String?,
    val id: Int?,
    val note: String?,
    @Json(name = "organisation_id")
    val organisationId: Int?,
    val room: BookingHistoryRoomData?,
    @Json(name = "room_id")
    val roomId: Int?,
    @Json(name = "start_date")
    val startDate: String?,
    val user: UserDto?
) {
    fun toBooking(): Booking {
        return Booking(
            id = id.toString(),
            roomName = room?.name ?: "",
            date = DateConverter.stringToDate(startDate ?: ""),
            endTime = DateConverter.stringToTime(endDate ?: ""),
            startTime = DateConverter.stringToTime(startDate ?: ""),
            imgUrl = room?.images?.randomOrNull()?.url ?: "",
            bookedBy = user?.email ?: "",
            note = note ?: "",
        )
    }
}
