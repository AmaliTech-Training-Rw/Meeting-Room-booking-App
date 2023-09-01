package com.amalitech.core.network

import com.amalitech.core.data.model.users.ApiUsers
import com.amalitech.core.util.ApiConstants
import retrofit2.http.GET

// TODO 3: replace these with actual routes
interface BookMeetingNetworkApi {
//     example api route
//    @FormUrlEncoded
//    @POST(ApiConstants.SEND_AIRTIME)
//    suspend fun sendAirtime(
//        @Header(ApiParameters.API_KEY) apiKey: String,
//        @Field(ApiParameters.USERNAME) username: String?,
//        @Field(ApiParameters.RECIPIENTS) recipients: String?
//    ): Response<SendAirtime>

    @GET(ApiConstants.USERS_ENDPOINT)
    suspend fun getUsers(): ApiUsers
}