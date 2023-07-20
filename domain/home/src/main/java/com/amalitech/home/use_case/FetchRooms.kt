package com.amalitech.home.use_case

import com.amalitech.home.model.Room
import com.amalitech.home.util.Response
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FetchRooms {
    operator fun invoke(): Flow<List<Room>> {
        return flowOf(
            listOf(
                Room(
                    "Room 1",
                    10,
                    "Air conditioning, Internet, Whiteboard, Natural light, Drinks",
                    ""
                ),
                Room(
                    "Room 1",
                    500,
                    "Air conditioning, Internet, Whiteboard, Natural light, Drinks",
                    ""
                ),
                Room(
                    "Room 1",
                    82,
                    "Air conditioning, Internet, Whiteboard, Natural light, Drinks",
                    ""
                ),
                Room(
                    "Room 1",
                    5,
                    "Air conditioning, Internet, Whiteboard, Natural light, Drinks",
                    ""
                )
            )
        )
    }

//    suspend operator fun invoke(): Response<List<Room>> {
//        // TODO(API call)
//        delay(5000)
//        return Response(generateRooms())
//    }

    private fun generateRooms(): List<Room> {
        return listOf(
            Room(
                "Room 1",
                10,
                "Air conditioning, Internet, Whiteboard, Natural light, Drinks",
                ""
            ),
            Room(
                "Room 1",
                500,
                "Air conditioning, Internet, Whiteboard, Natural light, Drinks",
                ""
            ),
            Room(
                "Room 1",
                82,
                "Air conditioning, Internet, Whiteboard, Natural light, Drinks",
                ""
            ),
            Room(
                "Room 1",
                5,
                "Air conditioning, Internet, Whiteboard, Natural light, Drinks",
                ""
            )
        )
    }
}
