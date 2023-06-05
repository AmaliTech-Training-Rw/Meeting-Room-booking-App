package com.amalitech.rooms.repository

import com.amalitech.core.data.model.Room


class RoomRepositoryImpl : RoomRepository {
    override fun getRooms(): List<Room> {

        return listOf(
            Room(
                roomName = "Room 1",
                numberOfPeople = 5,
                roomFeatures = "Air conditioning, Internet, Whiteboard, Natural light, Drinks",
                imageUrl = "https://res.cloudinary.com/dhw5h8j3v/image/upload/v1685995350/room_image_xfe71c.png"
            ),
            Room(
                roomName = " Room 2",
                numberOfPeople = 8,
                roomFeatures = "Air conditioning, Internet, Projector, Whiteboard, Snacks",
                imageUrl = "https://res.cloudinary.com/dhw5h8j3v/image/upload/v1685995350/room_image_xfe71c.png"
            ) ,
            Room(roomName = "Room 3",
            numberOfPeople = 6,
            roomFeatures = "Air conditioning, Internet, Whiteboard, Natural light, Drinks",
            imageUrl = "https://res.cloudinary.com/dhw5h8j3v/image/upload/v1685995350/room_image_xfe71c.png"
        ),
        Room(
            roomName = " Room 4",
            numberOfPeople = 12,
            roomFeatures = "Air conditioning, Internet, Projector, Whiteboard, Snacks",
            imageUrl = "https://res.cloudinary.com/dhw5h8j3v/image/upload/v1685995350/room_image_xfe71c.png"
        )

        )
    }
}
