package com.amalitech.onboarding.signup.use_case

import com.amalitech.onboarding.util.Result
import kotlinx.coroutines.delay

class FetchOrganization {


    suspend operator fun invoke(): Result<String> {
        // TODO("FETCH LIST OF ORGANIZATION FROM THE API")
        delay(3000)
        return Result.Success(
            listOf("organization 1", "organization 2", "organization 3")
        )
    }
}
