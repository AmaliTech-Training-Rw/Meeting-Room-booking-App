package com.amalitech.rooms.usecase

import android.content.Context
import com.amalitech.core.util.UiText
import com.amalitech.rooms.model.Room
import com.amalitech.rooms.repository.RoomRepository

class AddRoomUseCase(
    private val repos: RoomRepository
) {
    suspend operator fun invoke(
        room: Room,
        context: Context,
        updating: Boolean
    ): UiText? {
        return repos.addRoom(room, context, updating)
    }
}



