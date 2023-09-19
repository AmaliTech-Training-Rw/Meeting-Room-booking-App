package com.amalitech.dashboard.repository

import com.amalitech.core.util.ApiResult
import com.amalitech.dashboard.model.DashboardData

interface DashboardRepository {
    suspend fun fetchDashboardData(): ApiResult<DashboardData>
}
