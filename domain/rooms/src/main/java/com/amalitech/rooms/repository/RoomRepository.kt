package com.amalitech.rooms.repository

import android.content.Context
import com.amalitech.core.data.model.Room
import com.amalitech.core.util.ApiResult
import com.amalitech.core.util.UiText

interface RoomRepository{
    suspend fun getRooms(): ApiResult<List<Room>>

    suspend fun deleteRoom(room: Room): UiText?

    suspend fun addRoom(room: com.amalitech.rooms.model.Room, context: Context, updating: Boolean): UiText?

    suspend fun findRoom(id: String): ApiResult<Room>
}
