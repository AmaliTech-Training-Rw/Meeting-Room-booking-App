package com.amalitech.rooms.repository

import com.amalitech.core.data.model.Room
import com.amalitech.core.util.ApiResult
import com.amalitech.core.util.UiText

interface RoomRepository{
    suspend fun getRooms(): ApiResult<List<Room>>

    suspend fun deleteRoom(room: Room): UiText?
}