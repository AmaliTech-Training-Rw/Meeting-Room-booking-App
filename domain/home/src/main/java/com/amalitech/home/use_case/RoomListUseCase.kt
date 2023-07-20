package com.amalitech.home.use_case

import com.amalitech.core.domain.BookMeetingRepository

class GetRoomList constructor(
    private val repo: BookMeetingRepository
) {
    // operator fun invoke() = repo.getRooms()
}
