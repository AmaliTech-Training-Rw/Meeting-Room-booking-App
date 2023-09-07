package com.amalitech.rooms.remote

import com.amalitech.core.data.data_source.remote.dto.ApiSuccessResponseDto
import com.amalitech.core.data.data_source.remote.dto.RoomsDto
import com.amalitech.core.data.dto.ApiSuccessResponseDto
import com.amalitech.rooms.remote.dto.RoomsDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface RoomsApiService {
    @GET("rooms")
    suspend fun getRooms(): Response<RoomsDto>

    @DELETE("room/delete/{id}")
    suspend fun deleteRoom(@Path("id") id: Int): Response<ApiSuccessResponseDto>

    @Multipart
    @POST("room/create")
    suspend fun createRoom(
        @Part("room_name") roomName: RequestBody,
        @Part("capacity") capacity: RequestBody,
        @Part("location_id") locationId: RequestBody,
        @Part("features") features: RequestBody,
        @Part image: List<MultipartBody.Part>
    ): Response<ApiSuccessResponseDto>
}
