package com.amalitech.home.use_case

import com.amalitech.home.model.Booking
import com.amalitech.home.util.Response
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.Month
import kotlin.random.Random

class FetchBookings {

    suspend operator fun invoke(year: Int): Response<List<Booking>> {
        // TODO(API call)
        delay(5000)
        return Response(generateBookings(year))
    }

    private fun generateBookings(year: Int): List<Booking> {
        val roomNames = listOf("Room 1", "Room 2", "Room 3", "Room 4", "Room 5", "Room 6", "Room 7")
        val startDate = LocalDate.of(year, Month.JANUARY, 1)
        val endDate = LocalDate.of(startDate.year.plus(1), Month.DECEMBER, 31)

        val bookings = mutableListOf<Booking>()

        var currentDate = startDate
        while (bookings.size < 500 && currentDate.isBefore(endDate)) {
            val startTime = currentDate.atTime((1..12).random(), 0)
            val endTime = startTime.plusHours((1..4).random().toLong())
            val roomName = roomNames.random()

            bookings.add(Booking(startTime, endTime, roomName))

            // Add another booking on the same date
            if (bookings.size < 1500 && Math.random() < 0.5) {
                val startTime2 = currentDate.atTime((13..23).random(), 0)
                val endTime2 = startTime2.plusHours((1..4).random().toLong())
                val roomName2 = roomNames.random()

                bookings.add(Booking(startTime2, endTime2, roomName2))
            }

            currentDate = currentDate.plusDays(Random.nextLong(1, 5))
        }

        return bookings
    }
}
