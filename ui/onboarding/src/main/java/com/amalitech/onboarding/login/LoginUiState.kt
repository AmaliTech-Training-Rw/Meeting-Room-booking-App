package com.amalitech.onboarding.login

import com.amalitech.core.util.UiText
import com.amalitech.onboarding.components.AuthenticationBasedUiState

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val error: UiText? = null,
    val finishedLoggingIn: Boolean = false,
    val snackBarValue: UiText? = null
) {
    fun toBaseUiState(): AuthenticationBasedUiState {
        return AuthenticationBasedUiState(
            error, snackBarValue
        )
    }
}