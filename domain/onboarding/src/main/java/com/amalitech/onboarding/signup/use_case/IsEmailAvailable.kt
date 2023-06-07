package com.amalitech.onboarding.signup.use_case

class IsEmailAvailable {
    operator fun invoke(email: String): Boolean {
        // TODO("CHECK WITH THE API IF THE Email IS AVAILABLE")
        return true
    }
}
