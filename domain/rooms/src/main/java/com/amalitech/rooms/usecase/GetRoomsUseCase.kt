package com.amalitech.rooms.usecase


import com.amalitech.core.data.model.Room
import com.amalitech.rooms.repository.RoomRepository


class GetRoomsUseCase(private val roomRepository: RoomRepository) {


    operator fun invoke(): List<Room>{
        return roomRepository.getRooms()
    }
}
