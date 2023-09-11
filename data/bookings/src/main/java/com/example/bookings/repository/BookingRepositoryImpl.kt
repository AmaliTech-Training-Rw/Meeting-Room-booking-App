package com.example.bookings.repository

import com.amalitech.booking.model.Booking
import com.amalitech.booking.model.BookingRequestDetail
import com.amalitech.booking.repository.BookingRepository
import com.amalitech.core.data.repository.BaseRepo
import com.amalitech.core.util.ApiResult
import com.amalitech.core.util.UiText
import com.amalitech.core.util.extractError
import com.example.bookings.data_source.remote.BookingApiService

class BookingRepositoryImpl(
    private val api: BookingApiService
) : BookingRepository, BaseRepo() {
    override suspend fun fetchBookingRequests(): ApiResult<List<Booking>> {
        val result = safeApiCall(
            apiToBeCalled = {
                api.fetchBookingRequests()
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
            ApiResult(
                error = e.extractError()
            )
        }
    }

    override suspend fun approveBooking(id: String): UiText? {
        val result = safeApiCall(
            apiToBeCalled = {
                api.approveBooking(id.toIntOrNull() ?: -1)
            },
            extractError = {
                extractError(it)
            }
        )
        return result.error
    }

    override suspend fun declineBooking(id: String): UiText? {
        val result = safeApiCall(
            apiToBeCalled = {
                api.declineBooking(id.toIntOrNull() ?: -1)
            },
            extractError = {
                extractError(it)
            }
        )
        return result.error
    }

    override suspend fun fetchBookingHistory(): ApiResult<List<Booking>> {
        val result = safeApiCall(
            apiToBeCalled = {
                api.fetchBookingHistory()
            },
            extractError = {
                extractError(it)
            }
        )
        return try {
            ApiResult(
                data = result.data?.data?.map { it.toBooking() },
                error = result.error
            )
        } catch (e: Exception) {
            ApiResult(error = e.extractError())
        }
    }

    override suspend fun getBooking(id: String): ApiResult<BookingRequestDetail> {
        val result = safeApiCall(
            apiToBeCalled = {
                api.getBooking(id.toIntOrNull() ?: -1)
            },
            extractError = {
                extractError(it)
            }
        )
        return try {
            ApiResult(
                data = result.data?.data?.toBookingRequestDetail(),
                error = result.error
            )
        } catch (e: Exception) {
            ApiResult(
                error = e.extractError()
            )
        }
    }

    override suspend fun fetchUsersBookings(ended: Boolean): ApiResult<List<Booking>> {
        if (!ended) {
            val result = safeApiCall(
                apiToBeCalled = {
                    api.fetchUsersActiveBookings()
                },
                extractError = {
                    extractError(it)
                }
            )

            return try {
                ApiResult(
                    data = result.data?.data?.map { it.toBooking() } ?: emptyList(),
                    error = result.error
                )
            } catch (e: Exception) {
                ApiResult(error = e.extractError())
            }
        }
        // TODO(add api call for ended bookings)
        return ApiResult()
    }
}
