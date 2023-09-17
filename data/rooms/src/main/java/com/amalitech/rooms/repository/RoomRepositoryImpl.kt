package com.amalitech.rooms.repository

import android.content.Context
import com.amalitech.core.data.model.Room
import com.amalitech.core.data.repository.BaseRepo
import com.amalitech.core.domain.model.Booking
import com.amalitech.core.util.ApiResult
import com.amalitech.core.util.DateConverter
import com.amalitech.core.util.FileUtil
import com.amalitech.core.util.UiText
import com.amalitech.core.util.extractError
import com.amalitech.core.util.getUiText
import com.amalitech.rooms.model.Time
import com.amalitech.rooms.remote.RoomsApiService
import com.amalitech.rooms.remote.dto.BookingTimeDto
import com.amalitech.rooms.remote.dto.IntervalHour
import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.time.LocalDate
import java.time.LocalTime


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
                api.deleteRoom(room.id.toIntOrNull() ?: -1)
            },
            extractError = {
                extractError(it)
            }
        )
        return result.error
    }

    override suspend fun addRoom(
        room: com.amalitech.rooms.model.Room,
        context: Context,
        updating: Boolean
    ): UiText? {
        try {
            val image = (0 until room.imagesList.size).map {
                val file = FileUtil.getFile(context, room.imagesList[it])
                val compressedFile = FileUtil.saveBitmapToFile(file)
                val requestFile = compressedFile.asRequestBody()
                MultipartBody.Part.createFormData(
                    name = "room_image[]",
                    body = requestFile,
                    filename = file.name
                )
            }
            val result =
                if (updating)
                    safeApiCall(
                        apiToBeCalled = {
                            api.updateRoom(
                                roomName = room.name,
                                capacity = room.capacity,
                                locationId = room.location,
                                features = room.features,
                                image = image,
                                id = room.id
                            )
                        },
                        extractError = {
                            extractError(it)
                        }
                    )
                else
                    safeApiCall(
                        apiToBeCalled = {
                            api.createRoom(
                                roomName = room.name,
                                capacity = room.capacity,
                                locationId = room.location,
                                image = image,
                                *room.features.toTypedArray()
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

    override suspend fun findRoom(id: String): ApiResult<Room> {
        val result = safeApiCall(
            apiToBeCalled = {
                api.findRoom(id.toIntOrNull() ?: -1)
            },
            extractError = {
                extractError(it)
            }
        )
        return try {
            ApiResult(result.data?.data?.toRoom(), error = result.error)
        } catch (e: Exception) {
            ApiResult(error = e.getUiText())
        }
    }

    override suspend fun bookRoom(booking: Booking): UiText? {
        val date = DateConverter.dateToString(booking.date)
        val startTime = DateConverter.timeToString(booking.startTime)
        val endTime = DateConverter.timeToString(booking.endTime)
        val result = safeApiCall(
            apiToBeCalled = {
                api.bookRoom(
                    id = booking.roomId.toIntOrNull() ?: -1,
                    startDate = date,
                    endDate = date,
                    startTime = startTime,
                    endTime = endTime,
                    invited = booking.attendees,
                    note = booking.note
                )
            },
            extractError = {
                extractError(it)
            }
        )
        return result.error
    }

    override suspend fun getStartTime(roomId: String, date: LocalDate): ApiResult<List<Time>> {
        val stringDate = DateConverter.dateToString(date)

        val result = safeApiCall(
            apiToBeCalled = {
                api.getStartTimes(roomId.toIntOrNull() ?: -1, stringDate)
            },
            extractError = {
                extractError(it)
            }
        )
        return try {
            ApiResult(
                result.data?.intervalHours?.map {
                    Time(
                        time = DateConverter.timeStringToLocalTime(it.hour),
                        isAvailable = it.active
                    )
                },
                result.error
            )
        } catch (e: Exception) {
            ApiResult(error = e.extractError())
        }
    }

    override suspend fun getEndTime(
        startTime: LocalTime,
        intervalHour: List<Time>
    ): ApiResult<List<Time>> {
        val time = DateConverter.timeToString(startTime)
        val gson = Gson()
        val jsonObject = gson.toJson(
            BookingTimeDto(intervalHour.map {
            IntervalHour(
                active = it.isAvailable,
                hour = DateConverter.timeToString(it.time)
            )
        }))

        val result = safeApiCall(
            apiToBeCalled = {
                api.getEndTimes(
                    time = time,
                    interval = jsonObject)
            },
            extractError = {
                extractError(it)
            }
        )
        return try {
            ApiResult(
                result.data?.intervalHours?.map {
                    Time(
                        time = DateConverter.timeStringToLocalTime(it.hour),
                        isAvailable = it.active
                    )
                },
                result.error
            )
        } catch (e: Exception) {
            ApiResult(error = e.extractError())
        }
    }
}
