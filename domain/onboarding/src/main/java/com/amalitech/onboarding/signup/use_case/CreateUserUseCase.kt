package com.amalitech.onboarding.signup.use_case

import com.amalitech.core.util.UiText
import com.amalitech.onboarding.repository.OnboardingRepository

class CreateUserUseCase(private val repository: OnboardingRepository) {
    suspend operator fun invoke(
        token: String,
        username: String,
        password: String,
        passwordConfirmation: String,
    ): UiText? {
        return repository.createUser(
            username = username,
            password = password,
            passwordConfirmation = passwordConfirmation,
            token = token
        ).error
    }
}
