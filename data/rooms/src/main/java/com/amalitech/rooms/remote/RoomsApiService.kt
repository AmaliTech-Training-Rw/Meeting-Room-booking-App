package com.amalitech.rooms.remote

import com.amalitech.core.data.data_source.remote.dto.ApiSuccessResponseDto
import com.amalitech.core.data.data_source.remote.dto.FindRoomDto
import com.amalitech.core.data.data_source.remote.dto.RoomsDto
import com.amalitech.rooms.remote.dto.BookingTimeDto
import com.amalitech.rooms.remote.dto.IntervalHour
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

    @POST("booking/create")
    suspend fun bookRoom(
        @Query("room_id") id: Int,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("end_time") endTime: String,
        @Query("start_time") startTime: String,
        @Query("emails[]") invited: List<String>,
        @Query("note") note: String
    ): Response<ApiSuccessResponseDto>

    @POST("hours/{id}")
    suspend fun getStartTimes(
        @Path("id") roomId: Int,
        @Query("date") date: String
    ): Response<BookingTimeDto>

    @POST("/hours/endtime")
    suspend fun getEndTimes(
        @Query("start_time") time: String,
        @Query("intervalHours") interval: List<IntervalHour>
    ): Response<BookingTimeDto>
}
