package com.amalitech.rooms.usecase

import com.amalitech.core.util.UiText
import com.amalitech.rooms.model.Room
import com.amalitech.rooms.repository.RoomRepository

class AddRoomUseCase(
    private val repos: RoomRepository
) {
    suspend operator fun invoke(room: Room): UiText? {
        return repos.addRoom(room)
    }
}



