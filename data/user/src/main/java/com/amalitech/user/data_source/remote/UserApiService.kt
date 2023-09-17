package com.amalitech.user.data_source.remote

import com.amalitech.core.data.data_source.remote.dto.ApiSuccessResponseDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApiService {
    @GET("logout")
    suspend fun logout(): Response<ApiSuccessResponseDto>

    @GET("users")
    suspend fun fetchAllUsers(): Response<UsersListDto>

    @POST("user/invite")
    suspend fun addUser(
        @Query("first_name") firstName: String,
        @Query("last_name") lastName: String,
        @Query("email") email: String,
        @Query("location_id") locationId: Int,
        @Query("is_admin") isAdmin: Boolean
    ): Response<ApiSuccessResponseDto>

    @DELETE("user/delete/{id}")
    suspend fun deleteUser(
        @Path("id") id: Int
    ): Response<ApiSuccessResponseDto>

    @Multipart
    @POST("user/update")
    suspend fun updateProfileWithImage(
        @Query("first_name") firstName: String,
        @Query("last_name") lastName: String,
        @Query("title") title: String,
        @Query("password") password: String = "",
        @Query("password_confirmation") passwordConfirmation: String = "",
        @Query("current_password") currentPassword: String = "",
        @Part image: MultipartBody.Part?,
    ): Response<UpdateProfileDto>

    @POST("user/update")
    suspend fun updateProfileWithoutImage(
        @Query("first_name") firstName: String,
        @Query("last_name") lastName: String,
        @Query("title") title: String,
        @Query("password") password: String = "",
        @Query("password_confirmation") passwordConfirmation: String = "",
        @Query("current_password") currentPassword: String = "",
    ): Response<UpdateProfileDto>
}
