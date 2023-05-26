package com.amalitech.onboarding.reset_password

import com.amalitech.core.util.UiText

class ResetPassword {

    /**
     * Reset the user's password by making an API call
     * using the provided passwords
     * @param newPassword the new password
     * @param newPasswordConfirmation a confirmation of the new
     * password to make sure they match
     * @return an instance of UiText when there is an error returned
     * by the API, otherwise, null.
     */
    operator fun invoke(newPassword: String, newPasswordConfirmation: String): UiText? {
        // TODO(use the api to log into the account)
//        return UiText.DynamicString("The api is not yet available")
        return null
    }
}
