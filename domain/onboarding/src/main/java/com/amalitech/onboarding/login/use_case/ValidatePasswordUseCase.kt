package com.amalitech.onboarding.login.use_case

import com.amalitech.core.util.UiText
import com.amalitech.core.R


class ValidatePasswordUseCase {
    /**
     * Validates the password by checking if it's not blank
     * @param password The password that needs to be validated
     * @return an instance of UiText when the password is not
     * valid and null otherwise.
     */
    operator fun invoke(password: String): UiText? {
        val passwordPattern = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\d\\s])[A-Za-z\\d[^\\w\\d\\s]]{7,}$")
        return if (password.isBlank() || !password.matches(passwordPattern)) {
            UiText.StringResource(R.string.error_password_is_not_valid)
        } else
            null
    }
}