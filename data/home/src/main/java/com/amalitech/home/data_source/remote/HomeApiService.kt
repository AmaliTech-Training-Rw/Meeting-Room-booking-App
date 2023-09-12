package com.amalitech.home.data_source.remote

import com.amalitech.core.data.data_source.remote.dto.RoomsDto
import com.amalitech.home.data_source.remote.dto.CalendarBookingDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface HomeApiService {
    @POST("bookings/specificdate")
    suspend fun fetchBookings(
        @Query("specific_date") year: Int
    ): Response<CalendarBookingDto>

    @GET("rooms")
    suspend fun getRooms(): Response<RoomsDto>
}
