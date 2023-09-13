package com.amalitech.rooms.usecase

import com.amalitech.rooms.repository.RoomRepository

class FindRoomUseCase(
    private val repository: RoomRepository
) {

    suspend operator fun invoke(id: String) = repository.findRoom(id)
}
