package com.amalitech.home.book_room.use_case

import com.amalitech.home.book_room.model.BookableRoom
import com.amalitech.home.model.Booking
import com.amalitech.home.util.Response
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class GetBookableRoom {
    suspend operator fun invoke(roomId: String): Response<BookableRoom> {
        // TODO (API integration)
        delay(2000)
        return Response(
            data = BookableRoom(
                name = "Room",
                description = "description",
                features = listOf("Internet", "Drinks", "Air conditional"),
                bookings = listOf(
                    Booking(
                        startTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0)),
                        endTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0)).plusHours(1),
                        roomName = "room1"
                    ),
                    Booking(
                        startTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0)).plusHours(3),
                        endTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0)).plusHours(4),
                        roomName = "room2"
                    )
                ),
                imgUrl = "https://via.placehor.com/500.png"
            )
        )
    }
}
