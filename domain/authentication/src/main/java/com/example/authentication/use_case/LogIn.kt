package com.example.authentication.use_case

import com.amalitech.core.util.UiText

class LogIn {
    /**
     * Log the user into their account by making an API call
     * using the provided credentials
     * @param password the user password
     * @param email the user email
     * @return an instance of UiText when there is an error returned
     * by the API, otherwise, null.
     */
    operator fun invoke(email: String, password: String): UiText? {
        // TODO(use the api to log into the account)
        return UiText.DynamicString("The api is not yet available")
    }
}