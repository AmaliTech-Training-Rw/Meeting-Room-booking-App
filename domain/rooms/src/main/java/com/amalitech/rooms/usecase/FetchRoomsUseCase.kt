package com.amalitech.rooms.usecase


import com.amalitech.core.data.model.Room
import com.amalitech.core.util.Response
import com.amalitech.core.util.UiText
import com.amalitech.rooms.repository.RoomRepository


class FetchRoomsUseCase(private val roomRepository: RoomRepository) {

    suspend operator fun invoke(): Response<List<Room>> {
        return try {
            roomRepository.getRooms()
        } catch (e: Exception) {
            val localizedMessage = e.localizedMessage
            val errorMessage = if (localizedMessage != null)
                UiText.DynamicString(localizedMessage)
            else UiText.StringResource(com.amalitech.core.R.string.error_default_message)
            Response(error = errorMessage)
        }
    }
}
