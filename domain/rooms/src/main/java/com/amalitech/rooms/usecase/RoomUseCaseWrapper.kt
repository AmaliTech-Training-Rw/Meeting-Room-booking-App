package com.amalitech.rooms.usecase

data class RoomUseCaseWrapper(
    val fetchRoomsUseCase: FetchRoomsUseCase,
    val deleteRoomUseCase: DeleteRoomUseCase
)
