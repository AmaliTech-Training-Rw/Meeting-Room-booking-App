package com.amalitech.onboarding.reset_password

data class ResetPasswordUseCasesWrapper(
    val checkPasswordsMatchUseCase: CheckPasswordsMatchUseCase,
    val resetPasswordUseCase: ResetPasswordUseCase
)
