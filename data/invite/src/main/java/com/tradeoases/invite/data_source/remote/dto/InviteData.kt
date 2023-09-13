package com.tradeoases.invite.data_source.remote.dto

import com.amalitech.core.data.data_source.remote.dto.Attendee
import com.amalitech.core.data.data_source.remote.dto.BookingRoomData
import com.amalitech.core.data.data_source.remote.dto.DateConverter
import com.amalitech.core.data.data_source.remote.dto.UserDto
import com.squareup.moshi.Json
import com.tradeoases.invite.models.Invite

data class InviteData(
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
    fun toInvite(): Invite {
        return Invite(
            inviteId = id ?: -1,
            roomName =  room?.name ?:"",
            date = DateConverter.stringToDate(startDate ?: ""),
            startTime = DateConverter.stringToTime(startDate ?: ""),
            endTime = DateConverter.stringToTime(endDate ?: ""),
            imageUrl = room?.images?.randomOrNull()?.url ?: ""
        )
    }
}
