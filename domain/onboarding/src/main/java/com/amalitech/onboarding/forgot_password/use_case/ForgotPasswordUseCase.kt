package com.amalitech.onboarding.forgot_password.use_case

import com.amalitech.core.domain.use_case.ValidateEmail

data class ForgotPasswordUseCase(
    val sendResetLink: SendResetLink,
    val validateEmail: ValidateEmail
)
