package com.amalitech.bookmeetingroom.authentication_domain.use_case

import com.amalitech.bookmeetingroom.util.UiText

class SendResetLink {

    /**
     * Sends a reset link to the provided email address
     * by making an API call
     * @param email the provided email
     * @return an instance of UiText when there is an error returned
     * by the API, otherwise, null.
     */
    operator fun invoke(email: String): UiText? {
        // TODO(use the api to log into the account)
        return UiText.DynamicString("The api is not yet available")
    }
}
