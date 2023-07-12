package com.amalitech.core.domain.use_case

import android.util.Patterns
import androidx.annotation.VisibleForTesting
import com.amalitech.core.R
import com.amalitech.core.util.UiText

class ValidateEmailUseCase {
    /**
     * Validates email by checking if it's not blank and has an email form
     * @param email The email address that needs to be validated
     * @return an instance of UiText when the email is not
     * valid and null otherwise.
     */
    operator fun invoke(email: String): UiText? {
        return if (email.isNotBlank() && isEmailValid(email))
            null
        else
            UiText.StringResource(R.string.error_email_not_valid)
    }

    /**
     * Checks if an email has appropriate form
     * @param email The email address that needs to be checked
     * @return true if the email has appropriate form, null otherwise
     */
    @VisibleForTesting
    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
