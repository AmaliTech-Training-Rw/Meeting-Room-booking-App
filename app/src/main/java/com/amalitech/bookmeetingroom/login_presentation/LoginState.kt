package com.amalitech.bookmeetingroom.login_presentation

import com.amalitech.bookmeetingroom.util.UiText

data class LoginState(
    val email: String = "",
    val password: String = "",
    val error: UiText? = null
)
