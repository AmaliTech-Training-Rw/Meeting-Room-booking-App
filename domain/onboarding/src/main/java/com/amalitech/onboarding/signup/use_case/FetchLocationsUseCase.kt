package com.amalitech.onboarding.signup.use_case

import com.amalitech.core.util.ApiResult
import com.amalitech.onboarding.repository.OnboardingRepository
import com.amalitech.onboarding.signup.model.LocationX

class FetchLocationsUseCase(
    private val repos: OnboardingRepository
) {
    suspend operator fun invoke(): ApiResult<List<LocationX>> {
        return repos.fetchLocations()
    }
}