package com.amalitech.onboarding.reset_password

import com.amalitech.core.util.UiText

data class ResetPasswordUiState(
    val newPassword: String = "",
    val passwordConfirmation: String = "",
    val error: UiText? = null,
    val passwordReset: Boolean = false,
    val snackbarValue: UiText? = null
)
