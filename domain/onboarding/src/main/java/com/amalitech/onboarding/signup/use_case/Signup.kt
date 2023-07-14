package com.amalitech.onboarding.signup.use_case

import com.amalitech.core.util.UiText
import com.amalitech.onboarding.signup.model.User
import kotlinx.coroutines.delay

class Signup {

    suspend operator fun invoke(user: User): UiText? {
        // TODO("SIGN UP USING THE GIVEN INFORMATION AND CREATE A PROFILE")
        delay(5000)
        return null
    }
}
