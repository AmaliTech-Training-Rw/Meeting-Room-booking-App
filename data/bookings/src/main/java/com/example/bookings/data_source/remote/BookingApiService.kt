package com.example.bookings.data_source.remote

import com.amalitech.core.data.data_source.remote.dto.ApiSuccessResponseDto
import com.example.bookings.data_source.remote.dto.BookingDto
import com.example.bookings.data_source.remote.dto.BookingRequestDetailDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BookingApiService {

    @GET("bookings/requested")
    suspend fun fetchBookingRequests(): Response<BookingDto>

    @GET("booking/approve/{id}")
    suspend fun approveBooking(
        @Path("id") id: Int
    ): Response<ApiSuccessResponseDto>

    @GET("booking/decline/{id}")
    suspend fun declineBooking(
        @Path("id") id: Int
    ): Response<ApiSuccessResponseDto>

    @GET("bookings/history")
    suspend fun fetchBookingHistory(): Response<BookingDto>


    @GET("booking/find/{id}")
    suspend fun getBooking(
        @Path("id") id: Int
    ): Response<BookingRequestDetailDto>

    @GET("bookings")
    suspend fun fetchUsersActiveBookings(): Response<BookingDto>

    @GET("bookings/ended")
    suspend fun fetchUsersEndedBookings(): Response<BookingDto>
}
