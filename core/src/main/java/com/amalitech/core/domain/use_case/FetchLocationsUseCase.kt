package com.amalitech.core.domain.use_case

import com.amalitech.core.data.repository.CoreRepository
import com.amalitech.core.domain.model.LocationX
import com.amalitech.core.util.ApiResult

class FetchLocationsUseCase(
    private val repos: CoreRepository
) {
    suspend operator fun invoke(): ApiResult<List<LocationX>> {
        return repos.fetchLocations()
    }
}