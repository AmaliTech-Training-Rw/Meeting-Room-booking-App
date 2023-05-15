package com.amalitech.bookmeetingroom.login_domain.use_case

import android.text.TextUtils
import android.util.Patterns
import androidx.annotation.VisibleForTesting
import com.amalitech.bookmeetingroom.R
import com.amalitech.bookmeetingroom.util.UiText

class ValidateEmail {
    /**
     * Validates email by checking if it's not blank and has an email form
     * @param email The email address that needs to be validated
     * @return an instance of UiText when the email is not
     * valid and null otherwise.
     */
    operator fun invoke(email: String): UiText? {
        return if(email.isNotBlank() && isEmailValid(email))
            null
        else
            UiText.StringResource(R.string.error_email_not_valid)
    }

    @VisibleForTesting
    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
