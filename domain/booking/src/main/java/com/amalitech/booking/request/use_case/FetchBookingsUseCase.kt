package com.amalitech.booking.request.use_case

import com.amalitech.booking.model.Booking
import com.amalitech.core.util.ApiResult
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.LocalTime

class FetchBookingsUseCase {

    suspend operator fun invoke(): ApiResult<List<Booking>> {
        delay(3000)
        return ApiResult(
            data = listOf(
                Booking(
                    "room 1",
                    LocalDate.now(),
                    LocalTime.now(),
                    LocalTime.now().plusHours(4),
                    "https://via.placeholder.com/200.png",
                    "Ngomde Cadet Kamdaou"
                ),Booking(
                    "room 1",
                    LocalDate.now(),
                    LocalTime.now(),
                    LocalTime.now().plusHours(4),
                    "https://via.placeholder.com/200.png",
                    "Ngomde Cadet Kamdaou"
                ),Booking(
                    "room 1",
                    LocalDate.now(),
                    LocalTime.now(),
                    LocalTime.now().plusHours(4),
                    "https://via.placeholder.com/200.png",
                    "Ngomde Cadet Kamdaou"
                ),Booking(
                    "room 1",
                    LocalDate.now(),
                    LocalTime.now(),
                    LocalTime.now().plusHours(4),
                    "https://via.placeholder.com/200.png",
                    "Ngomde Cadet Kamdaou"
                ),Booking(
                    "room 1",
                    LocalDate.now(),
                    LocalTime.now(),
                    LocalTime.now().plusHours(4),
                    "https://via.placeholder.com/200.png",
                    "Ngomde Cadet Kamdaou"
                ),Booking(
                    "room 1",
                    LocalDate.now(),
                    LocalTime.now(),
                    LocalTime.now().plusHours(4),
                    "https://via.placeholder.com/200.png",
                    "Ngomde Cadet Kamdaou"
                ),Booking(
                    "room 1",
                    LocalDate.now(),
                    LocalTime.now(),
                    LocalTime.now().plusHours(4),
                    "https://via.placeholder.com/200.png",
                    "Ngomde Cadet Kamdaou"
                ),Booking(
                    "room 1",
                    LocalDate.now(),
                    LocalTime.now(),
                    LocalTime.now().plusHours(4),
                    "https://via.placeholder.com/200.png",
                    "Ngomde Cadet Kamdaou"
                ),
            )
        )
    }
}
