package com.example.bookings.data_source.remote.dto

import com.amalitech.booking.model.Booking
import com.squareup.moshi.Json
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


data class BookingRequestData(
    val approved: Int?,
    val declined: Int?,
    @Json(name = "end_date")
    val endDate: String,
    val id: Int?,
    val note: String?,
    @Json(name = "organisation_id")
    val organisationId: Int?,
    @Json(name = "room_id")
    val roomId: Int?,
    @Json(name = "start_date")
    val startDate: String,
    @Json(name = "user_id")
    val userId: Int?
) {
    fun toBooking(): Booking {
        val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return Booking(
            id = (id?:-1).toString(),
            roomName = "",
            date = LocalDate.parse(startDate, dateFormat),
            startTime = LocalTime.parse(startDate, dateFormat),
            endTime = LocalTime.parse(endDate, dateFormat),
            imgUrl = "",
        )
    }
}
