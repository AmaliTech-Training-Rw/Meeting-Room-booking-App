package com.amalitech.onboarding.forgot_password.use_case

import com.amalitech.core.domain.use_case.ValidateEmailUseCase

data class ForgotPasswordUseCasesWrapper(
    val sendResetLinkUseCase: SendResetLinkUseCase,
    val validateEmailUseCase: ValidateEmailUseCase
)
