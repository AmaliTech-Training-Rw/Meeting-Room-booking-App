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
        val images = listOf(
            "https://picsum.photos/id/29/4855/2000",
            "https://picsum.photos/id/0/4855/2000",
            "https://picsum.photos/id/15/4855/2000",
            "https://picsum.photos/id/26/4855/2000",
            "https://picsum.photos/id/3/4855/2000",
            "https://picsum.photos/id/4/4855/2000",
            "https://picsum.photos/id/5/4855/2000",
            "https://picsum.photos/id/6/4855/2000",
            "https://picsum.photos/id/7/4855/2000",
            "https://picsum.photos/id/8/4855/2000",
            "https://picsum.photos/id/9/4855/2000",
            "https://picsum.photos/id/10/4855/2000",
            "https://picsum.photos/id/11/4855/2000",
            "https://picsum.photos/id/12/4855/2000",
            "https://picsum.photos/id/13/4855/2000",
            "https://picsum.photos/id/1/4855/2000",
            "https://picsum.photos/id/14/4855/2000",
            "https://picsum.photos/id/16/4855/2000",
            "https://picsum.photos/id/17/4855/2000",
            "https://picsum.photos/id/10/4855/2000",
            "https://picsum.photos/id/18/4855/2000",
            "https://picsum.photos/id/19/4855/2000",
            "https://picsum.photos/id/20/4855/2000",
            "https://picsum.photos/id/22/4855/2000",
            "https://picsum.photos/id/9/4855/2000",
            "https://picsum.photos/id/23/4855/2000",
            "https://picsum.photos/id/24/4855/2000",
            "https://picsum.photos/id/25/4855/2000",
            "https://picsum.photos/id/27/4855/2000",
            "https://picsum.photos/id/28/4855/2000",
            "https://picsum.photos/id/33/4855/2000",
            "https://picsum.photos/id/30/4855/2000",
            "https://picsum.photos/id/31/4855/2000",
            "https://picsum.photos/id/32/4855/2000"
        )

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
                imgUrl = images.random(),
                capacity = 10
            )
        )
    }
}
