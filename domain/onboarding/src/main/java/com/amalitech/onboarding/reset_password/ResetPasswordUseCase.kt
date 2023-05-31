package com.amalitech.onboarding.reset_password

data class ResetPasswordUseCase(
    val checkPasswordsMatch: CheckPasswordsMatch,
    val resetPassword: ResetPassword
)
