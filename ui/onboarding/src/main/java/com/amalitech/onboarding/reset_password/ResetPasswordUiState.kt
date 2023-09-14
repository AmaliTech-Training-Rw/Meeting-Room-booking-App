package com.amalitech.onboarding.reset_password

data class ResetPasswordUiState(
    val newPassword: String = "",
    val passwordConfirmation: String = "",
    val token: String = ""
)
