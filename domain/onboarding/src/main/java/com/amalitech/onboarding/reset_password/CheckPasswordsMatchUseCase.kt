package com.amalitech.onboarding.reset_password

import com.amalitech.core.util.UiText

class CheckPasswordsMatchUseCase {

    /**
     * Checks if two passwords match
     * @param newPassword the new password
     * @param newPasswordConfirmation the confirmation of the new
     * password
     * @return an instance of UiText when the two passwords don't match,
     * otherwise, null.
     */
    operator fun invoke(newPassword: String, newPasswordConfirmation: String): UiText? {
        return if (newPassword == newPasswordConfirmation && newPassword.isNotBlank())
            null else
                UiText.StringResource(com.amalitech.core.R.string.error_passwords_dont_match)
    }
}