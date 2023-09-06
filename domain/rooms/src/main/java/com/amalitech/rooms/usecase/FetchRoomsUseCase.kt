package com.amalitech.rooms.usecase


import com.amalitech.core.data.model.Room
import com.amalitech.core.util.ApiResult
import com.amalitech.rooms.repository.RoomRepository


class FetchRoomsUseCase(private val roomRepository: RoomRepository) {

    suspend operator fun invoke(): ApiResult<List<Room>> {
        return roomRepository.getRooms()
    }
}
