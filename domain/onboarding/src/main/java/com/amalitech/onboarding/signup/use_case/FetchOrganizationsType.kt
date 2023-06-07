package com.amalitech.onboarding.signup.use_case

import kotlinx.coroutines.delay
import com.amalitech.onboarding.util.Result

class FetchOrganizationsType {


    suspend operator fun invoke(): Result<String> {
        // TODO("FETCH LIST OF ORGANIZATION FROM THE API")
        delay(3000)
        return Result.Success(
            listOf(
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
