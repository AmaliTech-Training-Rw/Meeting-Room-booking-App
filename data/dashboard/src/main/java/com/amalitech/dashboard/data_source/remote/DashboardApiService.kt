package com.amalitech.dashboard.data_source.remote

import com.amalitech.dashboard.data_source.remote.dto.DashboardDto
import retrofit2.Response
import retrofit2.http.GET

interface DashboardApiService {

    @GET("dashboard")
    suspend fun fetchDashboardData(): Response<DashboardDto>
}
