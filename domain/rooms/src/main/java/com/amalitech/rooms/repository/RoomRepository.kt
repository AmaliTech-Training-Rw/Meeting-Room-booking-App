package com.amalitech.rooms.repository

import com.amalitech.core.data.model.Room
import com.amalitech.core.util.Response
import com.amalitech.core.util.UiText

interface RoomRepository{
    suspend fun getRooms(): Response<List<Room>>

    suspend fun deleteRoom(room: Room): UiText?
}