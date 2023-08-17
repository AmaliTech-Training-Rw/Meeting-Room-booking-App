package com.amalitech.rooms.book_room.use_case

import com.amalitech.core.domain.model.Booking
import com.amalitech.core.util.Response
import com.amalitech.rooms.book_room.model.Room
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.LocalTime

class GetRoomUseCase {
    suspend operator fun invoke(roomId: String): Response<Room> {
        // TODO (API integration)
        delay(2000)
        return Response(
            data = Room(
                name = "Room",
                description = "description",
                features = listOf("Internet", "Drinks", "Air conditional"),
                bookings = listOf(
                    Booking(
                        startTime = LocalTime.of(9, 0),
                        endTime = LocalTime.of(9, 0).plusHours(1),
                        roomName = "room1",
                        roomId = "id1",
                        attendees = emptyList(),
                        note = "",
                        date = LocalDate.now()
                    ),
                    Booking(
                        startTime = LocalTime.of(9, 0).plusHours(3),
                        endTime = LocalTime.of(9, 0).plusHours(4),
                        roomName = "room2",
                        roomId = "id1",
                        attendees = emptyList(),
                        note = "",
                        date = LocalDate.now()
                    )
                ),
                imgUrl = "https://drive.google.com/file/d/1A_T0KtSBWT7_8meSVXjErC54yzrnWN28/view?usp=sharing",
                capacity = 10
            )
        )
    }
}
