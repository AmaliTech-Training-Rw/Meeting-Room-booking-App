package com.amalitech.bookmeetingroom.authentication_presentation.login

import com.amalitech.bookmeetingroom.util.UiText

data class LoginState(
    val email: String = "",
    val password: String = "",
    val error: UiText? = null
)
