package com.amalitech.booking.request.detail

import com.amalitech.booking.model.Booking
import com.amalitech.booking.model.BookingRequestDetail
import com.amalitech.core.data.model.Room
import com.amalitech.core.util.ApiResult
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.LocalTime

class GetBookingRequestDetailUseCase {
    suspend operator fun invoke(id: String): ApiResult<BookingRequestDetail> {
        delay(1000)
        return ApiResult(
            BookingRequestDetail(
                booking = Booking(
                    id = "id",
                    "room2",
                    LocalDate.now(),
                    LocalTime.of((0..23).random(), 0),
                    LocalTime.of((0..23).random(), 0),
                    "https://via.placehol.com/400.png",
                    bookedBy = "johndoe@amalitech.org",
                    attendees = listOf(
                        "johndoe@gmail.com",
                        "johndoe@example.com",
                        "johndoe@amalitech.com",
                        "johndoe@amalitech.org"
                    ),
                    note = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
                ),
                room = Room(
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
                    imageUrl = ""
                )
            )
        )
    }
}
