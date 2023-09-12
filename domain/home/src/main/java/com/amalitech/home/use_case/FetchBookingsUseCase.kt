package com.amalitech.home.use_case

import com.amalitech.core.domain.model.Booking
import com.amalitech.core.util.ApiResult
import com.amalitech.home.repository.HomeRepository

class FetchBookingsUseCase(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(year: Int): ApiResult<List<Booking>> {
        return repository.fetchBookings(year)
    }
}
