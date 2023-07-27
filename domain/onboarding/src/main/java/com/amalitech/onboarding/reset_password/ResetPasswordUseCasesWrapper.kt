package com.amalitech.onboarding.reset_password

import com.amalitech.onboarding.login.use_case.ValidatePasswordUseCase

data class ResetPasswordUseCasesWrapper(
    val checkPasswordsMatchUseCase: CheckPasswordsMatchUseCase,
    val resetPasswordUseCase: ResetPasswordUseCase,
    val validatePasswordUseCase: ValidatePasswordUseCase
)
