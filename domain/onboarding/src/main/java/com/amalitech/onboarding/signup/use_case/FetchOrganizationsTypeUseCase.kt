package com.amalitech.onboarding.signup.use_case

import com.amalitech.core.util.ApiResult
import kotlinx.coroutines.delay

class FetchOrganizationsTypeUseCase {


    suspend operator fun invoke(): ApiResult<List<String>> {
        // TODO("FETCH LIST OF ORGANIZATION FROM THE API")
        delay(3000)
        return ApiResult(
            data = listOf(
                "Non-profit organization",
                "Educational institution",
                "Government agency",
                "Healthcare provider",
                "Technology company",
                "Financial institution",
                "Retail business",
            )
        )
    }
}
