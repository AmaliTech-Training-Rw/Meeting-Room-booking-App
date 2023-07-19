package com.amalitech.rooms.repository

import com.amalitech.core.data.model.Room
import com.amalitech.core.util.ApiResult
import com.amalitech.core.util.UiText
import kotlinx.coroutines.delay


class RoomRepositoryImpl : RoomRepository {

    private val rooms = mutableListOf(
        Room(
            id = "id1",
            roomName = "Room 1",
            numberOfPeople = 5,
            roomFeatures = listOf(
                "Air conditioning",
                "Internet",
                "Whiteboard",
                "Natural light",
                "Drinks"
            ),
            imageUrl = "https://res.cloudinary.com/dhw5h8j3v/image/upload/v1685995350/room_image_xfe71c.png"
        ),
        Room(
            id = "id2",
            roomName = "Room 2",
            numberOfPeople = 8,
            roomFeatures = listOf(
                "Air conditioning",
                "Internet",
                "Projector",
                "Whiteboard",
                "Snacks"
            ),
            imageUrl = "https://res.cloudinary.com/dhw5h8j3v/image/upload/v1685995350/room_image_xfe71c.png"
        ),
        Room(
            id = "id3",
            roomName = "Room 3",
            numberOfPeople = 6,
            roomFeatures = listOf(
                "Air conditioning",
                "Internet",
                "Whiteboard",
                "Natural light",
                "Drinks"
            ),
            imageUrl = "https://res.cloudinary.com/dhw5h8j3v/image/upload/v1685995350/room_image_xfe71c.png"
        ),
        Room(
            id = "id4",
            roomName = "Room 4",
            numberOfPeople = 12,
            roomFeatures = listOf(
                "Air conditioning",
                "Internet",
                "Projector",
                "Whiteboard",
                "Snacks"
            ),
            imageUrl = "https://res.cloudinary.com/dhw5h8j3v/image/upload/v1685995350/room_image_xfe71c.png"
        )
    )

    override suspend fun getRooms(): ApiResult<List<Room>> {
        delay(2000)
        return ApiResult(rooms)

    }

    override suspend fun deleteRoom(room: Room): UiText? {
        var exception: Exception? = null
        try {
            rooms.remove(room)
        } catch (e: Exception) {
            exception = e
        }
        return if (exception == null)
            null else exception.localizedMessage?.let { UiText.DynamicString(it) }
    }
}
