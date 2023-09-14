package com.amalitech.onboarding.reset_password

import com.amalitech.core.util.UiText
import com.amalitech.onboarding.repository.OnboardingRepository

class ResetPasswordUseCase(
    private val repository: OnboardingRepository
) {

    /**
     * Reset the user's password by making an API call
     * using the provided passwords
     * @param newPassword the new password
     * @param newPasswordConfirmation a confirmation of the new
     * password to make sure they match
     * @return an instance of UiText when there is an error returned
     * by the API, otherwise, null.
     */
    suspend operator fun invoke(newPassword: String, newPasswordConfirmation: String, token: String): UiText? {
        return repository.resetPassword(newPassword, newPasswordConfirmation, token)
    }
}
