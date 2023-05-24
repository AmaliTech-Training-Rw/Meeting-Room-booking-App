package com.example.authentication.login

import com.example.authentication.util.UiText

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val error: UiText? = null,
    val finishedLoggingIn: Boolean = false,
    val snackBarValue: UiText? = null
)
