package com.amalitech.home.repository

import com.amalitech.core.data.model.Room
import com.amalitech.core.data.repository.BaseRepo
import com.amalitech.core.domain.model.Booking
import com.amalitech.core.util.ApiResult
import com.amalitech.core.util.extractError
import com.amalitech.home.data_source.remote.HomeApiService

class HomeRepositoryImpl(
    private val api: HomeApiService
): HomeRepository, BaseRepo(){
    override suspend fun fetchRooms(): ApiResult<List<Room>> {
        val result = safeApiCall(
            apiToBeCalled = {
                api.getRooms()
            },
            extractError = {
                extractError(it)
            }
        )
        return try {
            ApiResult(
                data = result.data?.data?.map { it.toRoom() },
                error = result.error
            )
        } catch (e: Exception) {
            ApiResult(error = e.extractError())
        }
    }

    override suspend fun fetchBookings(year: Int): ApiResult<List<Booking>> {
        val result = safeApiCall(
            apiToBeCalled = {
                api.fetchBookings(year)
            },
            extractError = {
                extractError(it)
            }
        )
        return try {
            ApiResult(
                result.data?.data?.map { it.toBooking() },
                result.error
            )
        } catch (e: Exception) {
            ApiResult(error = e.extractError())
        }
    }
}
