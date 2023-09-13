package com.amalitech.core.data.repository

import com.amalitech.core.data.data_source.remote.CoreApiService
import com.amalitech.core.util.ApiResult
import com.amalitech.core.util.UiText
import com.amalitech.core.domain.model.LocationX

class CoreRepository(
    private val api: CoreApiService
): BaseRepo() {
    suspend fun fetchLocations(): ApiResult<List<LocationX>> {
        val apiResult = safeApiCall(
            apiToBeCalled = {
                api.fetchLocations()
            },
            extractError = {
                return@safeApiCall UiText.StringResource(com.amalitech.core.R.string.error_default_message)
            }
        )
        return ApiResult(
            data = apiResult.data?.locations,
            error = apiResult.error
        )
    }
}
