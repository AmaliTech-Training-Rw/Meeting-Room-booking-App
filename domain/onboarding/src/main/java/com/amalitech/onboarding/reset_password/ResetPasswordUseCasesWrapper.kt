package com.amalitech.onboarding.reset_password

import com.amalitech.core.domain.use_case.CheckPasswordsMatchUseCase
import com.amalitech.core.domain.ValidatePasswordUseCase

data class ResetPasswordUseCasesWrapper(
    val checkPasswordsMatchUseCase: CheckPasswordsMatchUseCase,
    val resetPasswordUseCase: ResetPasswordUseCase,
    val validatePasswordUseCase: ValidatePasswordUseCase
)
