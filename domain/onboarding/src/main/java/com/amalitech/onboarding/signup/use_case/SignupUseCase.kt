package com.amalitech.onboarding.signup.use_case

import com.amalitech.core.util.UiText
import com.amalitech.onboarding.signup.model.User
import kotlinx.coroutines.delay

class SignupUseCase {

    suspend operator fun invoke(user: User): UiText? {
        // TODO("SIGN UP USING THE GIVEN INFORMATION")
        delay(5000)
        return null
    }
}
