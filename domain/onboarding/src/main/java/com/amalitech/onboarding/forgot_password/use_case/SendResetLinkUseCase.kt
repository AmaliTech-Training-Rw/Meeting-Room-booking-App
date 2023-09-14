package com.amalitech.onboarding.forgot_password.use_case

import com.amalitech.core.util.UiText
import com.amalitech.onboarding.repository.OnboardingRepository

class SendResetLinkUseCase(
    private val repository: OnboardingRepository
) {

    /**
     * Sends a reset link to the provided email address
     * by making an API call
     * @param email the provided email
     * @return an instance of UiText when there is an error returned
     * by the API, otherwise, null.
     */
    suspend operator fun invoke(email: String): UiText? {
        return repository.askResetLink(email)
    }
}
