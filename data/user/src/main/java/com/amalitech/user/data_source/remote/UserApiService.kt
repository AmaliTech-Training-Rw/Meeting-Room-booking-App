package com.amalitech.user.data_source.remote

import retrofit2.Response
import retrofit2.http.GET

interface UserApiService {
    @GET("logout")
    suspend fun logout(): Response<String>
}
