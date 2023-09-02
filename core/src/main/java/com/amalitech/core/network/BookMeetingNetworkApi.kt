package com.amalitech.core.network

import com.amalitech.core.data.model.users.ApiUsers
import com.amalitech.core.util.ApiConstants
import retrofit2.http.GET

// TODO 3: replace these with actual routes
interface BookMeetingNetworkApi {
    @GET(ApiConstants.USERS_ENDPOINT)
    suspend fun getUsers(): ApiUsers
}