package com.amalitech.onboarding.signup.use_case

import com.amalitech.core.util.UiText
import com.amalitech.onboarding.repository.OnboardingRepository
import com.amalitech.onboarding.signup.model.User

class SignupUseCase(private val onboardingRepository: OnboardingRepository) {

    suspend operator fun invoke(user: User): UiText? {
        val result = onboardingRepository.createOrganization(user)
        return result.error
    }
}
