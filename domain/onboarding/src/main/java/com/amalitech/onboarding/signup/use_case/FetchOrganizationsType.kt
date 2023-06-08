package com.amalitech.onboarding.signup.use_case

import com.amalitech.onboarding.util.Response
import kotlinx.coroutines.delay

class FetchOrganizationsType {


    suspend operator fun invoke(): Response<List<String>> {
        // TODO("FETCH LIST OF ORGANIZATION FROM THE API")
        delay(3000)
        return Response(
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
