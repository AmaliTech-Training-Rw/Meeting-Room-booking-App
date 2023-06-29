package com.amalitech.home.book_room.use_case

import com.amalitech.home.book_room.model.BookableRoom
import com.amalitech.home.model.Booking
import com.amalitech.home.util.Response
import kotlinx.coroutines.delay
import java.time.LocalDateTime

class GetBookableRoom {
    suspend operator fun invoke(roomId: String): Response<BookableRoom> {
        // TODO (API integration)
        delay(5000)
        return Response(
            data = BookableRoom(
                name = "Room",
                description = "description",
                features = listOf("Internet", "Drinks", "Air conditional"),
                bookings = listOf(
                    Booking(
                        startTime = LocalDateTime.now(),
                        endTime = LocalDateTime.now().plusHours(1),
                        roomName = "room1"
                    ),
                    Booking(
                        startTime = LocalDateTime.now().plusHours(3),
                        endTime = LocalDateTime.now().plusHours(4),
                        roomName = "room2"
                    )
                )
            )
        )
    }
}
