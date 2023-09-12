package com.amalitech.home.repository

import com.amalitech.core.data.model.Room
import com.amalitech.core.domain.model.Booking
import com.amalitech.core.util.ApiResult

interface HomeRepository {
    suspend fun fetchRooms(): ApiResult<List<Room>>

    suspend fun fetchBookings(year: Int): ApiResult<List<Booking>>
}
