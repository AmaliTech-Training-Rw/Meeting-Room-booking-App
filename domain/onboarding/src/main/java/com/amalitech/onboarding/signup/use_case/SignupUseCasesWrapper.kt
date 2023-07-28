package com.amalitech.onboarding.signup.use_case

import com.amalitech.core.domain.use_case.ValidateEmailUseCase
import com.amalitech.onboarding.login.use_case.ValidatePasswordUseCase
import com.amalitech.onboarding.reset_password.CheckPasswordsMatchUseCase

data class SignupUseCasesWrapper(
    val isEmailAvailableUseCase: IsEmailAvailableUseCase,
    val isUsernameAvailableUseCase: IsUsernameAvailableUseCase,
    val fetchOrganizationsTypeUseCase: FetchOrganizationsTypeUseCase,
    val signupUseCase: SignupUseCase,
    val validatePasswordUseCase: ValidatePasswordUseCase,
    val validateEmailUseCase: ValidateEmailUseCase,
    val checkPasswordsMatchUseCase: CheckPasswordsMatchUseCase,
    val checkValuesNotBlankUseCase: CheckValuesNotBlankUseCase
)
