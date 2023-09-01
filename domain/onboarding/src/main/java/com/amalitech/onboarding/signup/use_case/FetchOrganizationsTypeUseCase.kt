package com.amalitech.onboarding.signup.use_case

import com.amalitech.core.util.ApiResult
import com.amalitech.onboarding.repository.OnboardingRepository
import com.amalitech.onboarding.signup.model.TypesOrganisation

class FetchOrganizationsTypeUseCase(
    private val repos: OnboardingRepository
) {


    suspend operator fun invoke(): ApiResult<List<TypesOrganisation>> {
        val apiResult = repos.fetchOrganizationType()
        return ApiResult(data = apiResult.data?.typesOfOrganization, error = apiResult.error)
    }
}
