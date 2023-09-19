package com.amalitech.dashboard.repository

import com.amalitech.core.data.repository.BaseRepo
import com.amalitech.core.util.ApiResult
import com.amalitech.core.util.extractError
import com.amalitech.dashboard.data_source.remote.DashboardApiService
import com.amalitech.dashboard.model.DashboardData

class DashboardRepositoryImpl(
    private val api: DashboardApiService
): BaseRepo(), DashboardRepository {
    override suspend fun fetchDashboardData(): ApiResult<DashboardData> {
        val result = safeApiCall(
            apiToBeCalled = {
                api.fetchDashboardData()
            },
            extractError = {
                extractError(it)
            }
        )
        return try {
            ApiResult(
                result.data?.toDomainData(),
                result.error
            )
        } catch (e: Exception) {
            ApiResult(error = e.extractError())
        }
    }
}
