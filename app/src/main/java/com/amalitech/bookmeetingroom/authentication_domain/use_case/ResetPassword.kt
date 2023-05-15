package com.amalitech.bookmeetingroom.authentication_domain.use_case

import com.amalitech.bookmeetingroom.util.UiText

class ResetPassword {
    operator fun invoke(newPassword: String, newPasswordConfirmation: String): UiText? {
        // TODO(use the api to log into the account)
//        return UiText.DynamicString("The api is not yet available")
        return null
    }
}
