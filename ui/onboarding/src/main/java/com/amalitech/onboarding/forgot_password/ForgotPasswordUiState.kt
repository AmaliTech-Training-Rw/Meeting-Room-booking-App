package com.amalitech.onboarding.forgot_password

import com.amalitech.core.util.UiText
import com.amalitech.onboarding.components.AuthenticationBasedUiState

data class ForgotPasswordUiState(
    val email: String = "",
    val error: UiText? = null,
    val snackBarValue: UiText? = null,
    val linkSent: Boolean = false
) {
    fun toBaseUiState(): AuthenticationBasedUiState {
        return AuthenticationBasedUiState(
            error, snackBarValue
        )
    }
}
