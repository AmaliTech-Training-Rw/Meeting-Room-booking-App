package com.tradeoases.invite.data_source.remote

import com.tradeoases.invite.data_source.remote.dto.InviteDto
import retrofit2.Response
import retrofit2.http.GET

interface InviteApiService {

    @GET("bookings/invited")
    suspend fun fetchInvitations(): Response<InviteDto>
}
