package com.amalitech.onboarding.signup.use_case

class IsUsernameAvailable {

    operator fun invoke(username: String): Boolean {
        // TODO("CHECK WITH THE API IF THE USERNAME IS AVAILABLE")
        return true
    }
}
