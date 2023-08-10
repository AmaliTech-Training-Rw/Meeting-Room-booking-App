package com.amalitech.onboarding.login.use_case

import com.amalitech.core.domain.use_case.ValidateEmailUseCase

data class LoginUseCasesWrapper(
    val logInUseCase: LogInUseCase,
    val validateEmailUseCase: ValidateEmailUseCase,
    val validatePasswordUseCase: ValidatePasswordUseCase,
    val isUserAdminUseCase: IsUserAdminUseCase,
    val loadProfileInformationUseCase: LoadProfileInformationUseCase
)
