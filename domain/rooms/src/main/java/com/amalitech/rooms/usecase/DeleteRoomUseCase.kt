package com.amalitech.rooms.usecase

import com.amalitech.core.data.model.Room
import com.amalitech.core.util.UiText
import com.amalitech.rooms.repository.RoomRepository

class DeleteRoomUseCase(private val roomRepository: RoomRepository) {
    suspend operator fun invoke(room: Room): UiText? {
        return roomRepository.deleteRoom(room)
    }
}
