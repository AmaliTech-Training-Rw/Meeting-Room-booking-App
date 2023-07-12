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
        val date1 = LocalDate.now().plusDays(2)
        return if (ended)
            Response(
                data = listOf(
                    Booking(
                        "room1",
                        date,
                        LocalTime.of((0..23).random(), 0),
                        LocalTime.of((0..23).random(), 0),
                        "https://via.placeholder.com/500.png"
                    ),
                    Booking(
                        "room2",
                        date,
                        LocalTime.of((0..23).random(), 0),
                        LocalTime.of((0..23).random(), 0),
                        "https://via.placeholder.com/500.png"
                    ),
                    Booking(
                        "room1",
                        date,
                        LocalTime.of((0..23).random(), 0),
                        LocalTime.of((0..23).random(), 0),
                        "https://via.placeholder.com/500.png"
                    ),
                    Booking(
                        "room1",
                        date,
                        LocalTime.of((0..23).random(), 0),
                        LocalTime.of((0..23).random(), 0),
                        "https://via.placeholder.com/500.png"
                    ),
                    Booking(
                        "room2",
                        date,
                        LocalTime.of((0..23).random(), 0),
                        LocalTime.of((0..23).random(), 0),
                        "https://via.placeholder.com/500.png"
                    ),
                    Booking(
                        "room2",
                        date,
                        LocalTime.of((0..23).random(), 0),
                        LocalTime.of((0..23).random(), 0),
                        "https://via.placeholder.com/500.png"
                    ),
                    Booking(
                        "room1",
                        date,
                        LocalTime.of((0..23).random(), 0),
                        LocalTime.of((0..23).random(), 0),
                        "https://via.placeholder.com/500.png"
                    ),
                )
            ) else Response(
            data = listOf(
                Booking(
                    "room3",
                    date1,
                    LocalTime.of((0..12).random(), 0),
                    LocalTime.of((0..23).random(), 0),
                    "https://via.placeholder.com/400.png"
                ),
                Booking(
                    "room2",
                    date1,
                    LocalTime.of((0..23).random(), 0),
                    LocalTime.of((0..23).random(), 0),
                    "https://via.placeholder.com/400.png"
                ),
                Booking(
                    "room1",
                    date1,
                    LocalTime.of((0..23).random(), 0),
                    LocalTime.of((0..23).random(), 0),
                    "https://via.placeholder.com/400.png"
                ),
                Booking(
                    "room1",
                    date1,
                    LocalTime.of((0..23).random(), 0),
                    LocalTime.of((0..23).random(), 0),
                    "https://via.placeholder.com/400.png"
                ),
                Booking(
                    "room2",
                    date1,
                    LocalTime.of((0..23).random(), 0),
                    LocalTime.of((0..23).random(), 0),
                    "https://via.placeholder.com/400.png"
                ),
                Booking(
                    "room2",
                    date1,
                    LocalTime.of((0..23).random(), 0),
                    LocalTime.of((0..23).random(), 0),
                    "https://via.placeholder.com/400.png"
                ),
                Booking(
                    "room1",
                    date1,
                    LocalTime.of((0..23).random(), 0),
                    LocalTime.of((0..23).random(), 0),
                    "https://via.placeholder.com/400.png"
                ),
            )
        )
    }
}
