package com.amalitech.rooms.remote

import com.amalitech.core.data.data_source.remote.dto.ApiSuccessResponseDto
import com.amalitech.core.data.data_source.remote.dto.RoomsDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface RoomsApiService {
    @GET("rooms")
    suspend fun getRooms(): Response<RoomsDto>

    @DELETE("room/delete/{id}")
    suspend fun deleteRoom(@Path("id") id: Int): Response<ApiSuccessResponseDto>

    @Multipart
    @POST("room/create")
    suspend fun createRoom(
        @Query("name") roomName: String,
        @Query("capacity") capacity: Int,
        @Query("location_id") locationId: Int,
        @Query("features[]") features: List<String>,
        @Part image: List<MultipartBody.Part>
    ): Response<ApiSuccessResponseDto>

    @Multipart
    @POST("room/update/{id}")
    suspend fun updateRoom(
        @Path("id") id: Int,
        @Query("name") roomName: String,
        @Query("capacity") capacity: Int,
        @Query("location_id") locationId: Int,
        @Query("features[]") features: List<String>,
        @Query("_method") method: String = "PUT",
        @Part image: List<MultipartBody.Part>
    ): Response<ApiSuccessResponseDto>

    @GET("room/find/{id}")
    suspend fun findRoom(@Path("id") id: Int): Response<FindRoomDto>
}
