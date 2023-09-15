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
import com.amalitech.rooms.remote.dto.IntervalHour
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
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
                                features = room.features,
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

        val result = safeApiCall(
            apiToBeCalled = {
                api.getEndTimes(
                    time = time,
                    interval = intervalHour.map {
                        IntervalHour(
                            active = it.isAvailable,
                            hour = DateConverter.timeToString(it.time)
                        )
                    })
            },
            extractError = {
                extractError(it)
            }
        )
        return try {
            ApiResult(
                result.data?.intervalHours?.map {
                    Time(
                        time = DateConverter.stringToTime(it.hour),
                        isAvailable = it.active
                    )
                },
                result.error
            )
        } catch (e: Exception) {
            ApiResult(error = e.extractError())
        }
    }

    private fun saveBitmapToFile(file: File): File {
        return try {
            // BitmapFactory options to downsize the image
            val o = BitmapFactory.Options()
            o.inJustDecodeBounds = true
            o.inSampleSize = 6
            // factor of downsizing the image
            var inputStream = FileInputStream(file)
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o)
            inputStream.close()

            // The new size we want to scale to
            val REQUIRED_SIZE = 75

            // Find the correct scale value. It should be the power of 2.
            var scale = 1
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                o.outHeight / scale / 2 >= REQUIRED_SIZE
            ) {
                scale *= 2
            }
            val o2 = BitmapFactory.Options()
            o2.inSampleSize = scale
            inputStream = FileInputStream(file)
            val selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2)
            inputStream.close()

            // here i override the original image file
            file.createNewFile()
            val outputStream = FileOutputStream(file)
            selectedBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            file
        } catch (e: java.lang.Exception) {
            file
        }
    }

    @Throws(IOException::class)
    fun getFile(context: Context, uri: Uri): File {
        val destinationFilename =
            File(context.filesDir.path + File.separatorChar + queryName(context, uri))
        try {
            context.contentResolver.openInputStream(uri).use { ins ->
                createFileFromStream(
                    ins!!,
                    destinationFilename
                )
            }
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
        return destinationFilename
    }

    private fun createFileFromStream(ins: InputStream, destination: File?) {
        try {
            FileOutputStream(destination).use { os ->
                val buffer = ByteArray(4096)
                var length: Int
                while (ins.read(buffer).also { length = it } > 0) {
                    os.write(buffer, 0, length)
                }
                os.flush()
            }
        } catch (ex: java.lang.Exception) {
            Log.e("Save File", ex.message!!)
            ex.printStackTrace()
        }
    }

    private fun queryName(context: Context, uri: Uri): String {
        val returnCursor = context.contentResolver.query(uri, null, null, null, null)!!
        val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        val name = returnCursor.getString(nameIndex)
        returnCursor.close()
        return name
    }
}
