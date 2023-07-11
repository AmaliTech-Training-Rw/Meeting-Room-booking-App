package com.amalitech.onboarding.reset_password

import com.amalitech.onboarding.login.use_case.ValidatePassword

data class ResetPasswordUseCase(
    val checkPasswordsMatch: CheckPasswordsMatch,
    val resetPassword: ResetPassword,
    val validatePassword: ValidatePassword
)
