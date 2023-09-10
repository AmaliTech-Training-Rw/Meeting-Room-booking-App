package com.example.bookings.data_source.remote

import com.amalitech.core.data.data_source.remote.dto.ApiSuccessResponseDto
import com.example.bookings.data_source.remote.dto.BookingHistoryDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BookingApiService {

    @GET("bookings/requested")
    suspend fun fetchBookingRequests(): Response<BookingHistoryDto>

    @GET("booking/approve/{id}")
    suspend fun approveBooking(
        @Path("id") id: Int
    ): Response<ApiSuccessResponseDto>

    @GET("booking/decline/{id}")
    suspend fun declineBooking(
        @Path("id") id: Int
    ): Response<ApiSuccessResponseDto>

    @GET("bookings/history")
    suspend fun fetchBookingHistory(): Response<BookingHistoryDto>
}
