package com.amalitech.bookmeetingroom.login_domain.use_case

import android.text.TextUtils
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
        return if(!TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            null
        else
            UiText.StringResource(R.string.error_email_not_valid)
    }
}
