package com.amalitech.home.use_case

import com.amalitech.home.repository.HomeRepository

class FetchRoomsUseCase(
    private val repository: HomeRepository
) {
    suspend operator fun invoke() = repository.fetchRooms()
}
