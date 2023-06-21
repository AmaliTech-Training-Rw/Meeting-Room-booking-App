package com.amalitech.rooms.repository

import com.amalitech.core.data.model.Room

interface RoomRepository{
    fun getRooms(): List<Room>
}