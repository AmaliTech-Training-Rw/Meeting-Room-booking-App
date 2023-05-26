package com.example.onboarding_presentation.forgot_password

import com.amalitech.core.util.UiText

data class ForgotPasswordUiState(
    val email: String = "",
    val error: UiText? = null,
    val snackBarValue: UiText? = null,
    val linkSent: Boolean = false
)
