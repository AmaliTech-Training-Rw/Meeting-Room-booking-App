package com.amalitech.bookmeetingroom.authentication_presentation.forgot_password

import com.amalitech.bookmeetingroom.util.UiText

data class ForgotPasswordState(
    val email: String = "",
    val error: UiText? = null
)
