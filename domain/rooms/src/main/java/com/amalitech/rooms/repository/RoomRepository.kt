package com.amalitech.rooms.repository

import android.content.Context
import com.amalitech.core.data.model.Room
import com.amalitech.core.domain.model.Booking
import com.amalitech.core.util.ApiResult
import com.amalitech.core.util.UiText
import com.amalitech.rooms.model.Time
import java.time.LocalDate
import java.time.LocalTime

interface RoomRepository{
    suspend fun getRooms(): ApiResult<List<Room>>

    suspend fun deleteRoom(room: Room): UiText?

    suspend fun addRoom(room: com.amalitech.rooms.model.Room, context: Context, updating: Boolean): UiText?

    suspend fun findRoom(id: String): ApiResult<Room>

    suspend fun bookRoom(booking: Booking): UiText?
    suspend fun getStartTime(roomId: String, date: LocalDate): ApiResult<List<Time>>
    suspend fun getEndTime(startTime: LocalTime, intervalHour: List<Time>): ApiResult<List<Time>>
}
