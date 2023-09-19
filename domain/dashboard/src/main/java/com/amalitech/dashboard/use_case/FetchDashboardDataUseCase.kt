package com.amalitech.dashboard.use_case

import com.amalitech.core.util.ApiResult
import com.amalitech.dashboard.model.DashboardData
import com.amalitech.dashboard.repository.DashboardRepository

class FetchDashboardDataUseCase(
    private val repository: DashboardRepository
) {
    suspend operator fun invoke(): ApiResult<DashboardData> {
        return repository.fetchDashboardData()
    }
}
