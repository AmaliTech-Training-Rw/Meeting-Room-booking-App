package com.amalitech.bookmeetingroom.authentication_domain.use_case

import com.amalitech.bookmeetingroom.R
import com.amalitech.bookmeetingroom.util.UiText

class CheckPasswordsMatch {

    operator fun invoke(newPassword: String, newPasswordConfirmation: String): UiText? {
        return if (newPassword == newPasswordConfirmation && newPassword.isNotBlank())
            null else
                UiText.StringResource(R.string.error_passwords_dont_match)
    }
}