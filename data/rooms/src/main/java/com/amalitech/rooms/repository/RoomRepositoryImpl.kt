package com.amalitech.rooms.repository

import com.amalitech.core.data.model.Room
import com.amalitech.core.data.repository.BaseRepo
import com.amalitech.core.util.ApiResult
import com.amalitech.core.util.UiText
import com.amalitech.rooms.remote.RoomsApiService


class RoomRepositoryImpl(
    private val api: RoomsApiService
) : RoomRepository, BaseRepo() {

    override suspend fun getRooms(): ApiResult<List<Room>> {
        val result = safeApiCall(
            apiToBeCalled = {
                api.getRooms()
            },
            extractError = {
                extractError(it)
            }
        )
        try {
        return ApiResult(
            data = result.data?.data?.map { it.toRoom() },
            error = result.error
        )
         } catch (e: Exception) {
            val localizedMessage = e.localizedMessage
            if (localizedMessage != null)
                return ApiResult(error = UiText.DynamicString(localizedMessage))
            return ApiResult(error = UiText.StringResource(com.amalitech.core.R.string.error_default_message))
        }
    }

    override suspend fun deleteRoom(room: Room): UiText? {
        val result = safeApiCall(
            apiToBeCalled = {
                api.deleteRoom(room.id.toIntOrNull()?:-1)
            },
            extractError = {
                extractError(it)
            }
        )
        return result.error
    }
}
