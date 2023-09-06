package com.amalitech.rooms.remote

import com.amalitech.core.data.dto.ApiSuccessResponseDto
import com.amalitech.rooms.remote.dto.RoomsDto
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface RoomsApiService {
    @GET("rooms")
    suspend fun getRooms(): Response<RoomsDto>

    @DELETE("room/delete/{id}")
    suspend fun deleteRoom(@Path("id") id: Int): Response<ApiSuccessResponseDto>
}
