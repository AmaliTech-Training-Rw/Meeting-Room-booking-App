package com.amalitech.rooms.book_room.use_case

import com.amalitech.core.util.ApiResult
import com.amalitech.rooms.model.Time
import com.amalitech.rooms.repository.RoomRepository
import java.time.LocalTime

class GetEndTimeUseCase(
    private val repository: RoomRepository
) {
    suspend operator fun invoke(startTime: LocalTime, interval: List<Time>): ApiResult<List<Time>> =
        repository.getEndTime(startTime, intervalHour = interval)
}

