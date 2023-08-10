package com.amalitech.booking.use_case

import com.amalitech.booking.model.Booking
import com.amalitech.core.util.Response
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.LocalTime

class GetBookingsUseCase {

    suspend operator fun invoke(ended: Boolean = false): Response<List<Booking>> {
        delay(2000)
        val date = LocalDate.now()
        return if (ended)
            Response(data = emptyList()) else Response(
            data = listOf(
                Booking(
                    id = "id",
                    "room3",
                    date,
                    LocalTime.of((0..12).random(), 0),
                    LocalTime.of((0..23).random(), 0),
                    "https://via.placehol.com/400.png"
                ),
                Booking(
                    id = "id",
                    "room2",
                    date,
                    LocalTime.of((0..23).random(), 0),
                    LocalTime.of((0..23).random(), 0),
                    "https://via.placehol.com/400.png"
                ),
                Booking(
                    id = "id",
                    "room1",
                    date,
                    LocalTime.of((0..23).random(), 0),
                    LocalTime.of((0..23).random(), 0),
                    "https://via.placehol.com/400.png"
                ),
                Booking(
                    id = "id",
                    "room1",
                    date,
                    LocalTime.of((0..23).random(), 0),
                    LocalTime.of((0..23).random(), 0),
                    "https://via.placehol.com/400.png"
                ),
                Booking(
                    id = "id",
                    "room2",
                    date,
                    LocalTime.of((0..23).random(), 0),
                    LocalTime.of((0..23).random(), 0),
                    "https://via.placehol.com/400.png"
                ),
                Booking(
                    id = "id",
                    "room2",
                    date,
                    LocalTime.of((0..23).random(), 0),
                    LocalTime.of((0..23).random(), 0),
                    "https://via.placehol.com/400.png"
                ),
                Booking(
                    id = "id",
                    "room1",
                    date,
                    LocalTime.of((0..23).random(), 0),
                    LocalTime.of((0..23).random(), 0),
                    "https://via.placehol.com/400.png"
                ),
            )
        )
    }
}
