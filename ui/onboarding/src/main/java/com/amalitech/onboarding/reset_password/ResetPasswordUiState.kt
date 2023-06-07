package com.amalitech.onboarding.reset_password

import com.amalitech.core.util.UiText
import com.amalitech.onboarding.components.AuthenticationBasedUiState

data class ResetPasswordUiState(
    val newPassword: String = "",
    val passwordConfirmation: String = "",
    val error: UiText? = null,
    val passwordReset: Boolean = false,
    val snackbarValue: UiText? = null
) {
    fun toBaseUiState(): AuthenticationBasedUiState {
        return AuthenticationBasedUiState(
            error, snackbarValue
        )
    }
}
