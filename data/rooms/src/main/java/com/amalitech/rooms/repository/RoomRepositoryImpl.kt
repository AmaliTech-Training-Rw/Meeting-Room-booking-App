package com.amalitech.rooms.repository

import com.amalitech.core.data.model.Room
import com.amalitech.core.data.repository.BaseRepo
import com.amalitech.core.util.ApiResult
import com.amalitech.core.util.UiText
import com.amalitech.core.util.getUiText
import com.amalitech.rooms.remote.RoomsApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


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

    override suspend fun addRoom(room: com.amalitech.rooms.model.Room): UiText? {
        try {
            val image = (0 until room.imagesList.size).map {
                val file = File(room.imagesList[it].path ?: "")
                val requestFile = file.asRequestBody()
                MultipartBody.Part.createFormData(
                    name = "room_image[]",
                    body = requestFile,
                    filename = file.name
                )
            }
            val name = room.name.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val capacity = room.capacity.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val locationId = room.location.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val features = room.features.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val result = safeApiCall(
                apiToBeCalled = {
                    api.createRoom(
                        roomName = name,
                        capacity = capacity,
                        locationId = locationId,
                        features = features,
                        image = image
                    )
                },
                extractError = {
                    extractError(it)
                }
            )
            return result.error
        } catch (e: Exception) {
            return e.getUiText()
        }
    }
}
