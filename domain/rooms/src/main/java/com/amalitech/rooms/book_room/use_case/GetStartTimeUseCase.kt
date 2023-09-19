package com.amalitech.rooms.book_room.use_case

import com.amalitech.core.util.ApiResult
import com.amalitech.rooms.model.Time
import com.amalitech.rooms.repository.RoomRepository
import java.time.LocalDate

class GetStartTimeUseCase(
    private val repository: RoomRepository
) {
    suspend operator fun invoke(roomId: String, date: LocalDate): ApiResult<List<Time>> =
        repository.getStartTime(roomId, date)
}
