package com.example.bookings.data_source.remote.dto

import com.amalitech.booking.model.Booking
import com.amalitech.booking.model.BookingRequestDetail
import com.amalitech.core.data.model.Room
import com.amalitech.core.util.DateConverter
import com.amalitech.core.data.data_source.remote.dto.Attendee
import com.amalitech.core.data.data_source.remote.dto.BookingRoomData
import com.amalitech.core.data.data_source.remote.dto.DateConverter
import com.amalitech.core.data.data_source.remote.dto.UserDto
import com.amalitech.core.data.model.Room
import com.squareup.moshi.Json

data class BookingData(
    val approved: Int?,
    val attendees: List<Attendee>?,
    val declined: Int?,
    @Json(name = "end_date")
    val endDate: String?,
    val id: Int?,
    val note: String?,
    @Json(name = "organisation_id")
    val organisationId: Int?,
    val room: BookingRoomData?,
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
            attendees = attendees?.map { it.email ?: "" } ?: emptyList()
        )
    }

    fun toBookingRequestDetail(): BookingRequestDetail {
        return BookingRequestDetail(
            booking = toBooking(),
            room = Room(
                id = room?.id.toString(),
                roomName = room?.name ?: "",
                numberOfPeople = room?.capacity ?: 0,
                roomFeatures = room?.features?.map { it.name } ?: emptyList(),
                imageUrl = room?.images?.randomOrNull()?.url ?: ""
            )
        )
    }
}
