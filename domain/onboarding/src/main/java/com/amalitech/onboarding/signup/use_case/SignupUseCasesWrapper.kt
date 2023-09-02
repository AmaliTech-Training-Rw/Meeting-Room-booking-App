package com.amalitech.onboarding.signup.use_case

import com.amalitech.core.domain.use_case.ValidateEmailUseCase
import com.amalitech.core.domain.ValidatePasswordUseCase
import com.amalitech.core.domain.use_case.CheckPasswordsMatchUseCase
import com.amalitech.core.domain.use_case.CheckValuesNotBlankUseCase

data class SignupUseCasesWrapper(
    val isEmailAvailableUseCase: IsEmailAvailableUseCase,
    val isUsernameAvailableUseCase: IsUsernameAvailableUseCase,
    val fetchOrganizationsTypeUseCase: FetchOrganizationsTypeUseCase,
    val signupUseCase: SignupUseCase,
    val validatePasswordUseCase: ValidatePasswordUseCase,
    val validateEmailUseCase: ValidateEmailUseCase,
    val checkPasswordsMatchUseCase: CheckPasswordsMatchUseCase,
    val checkValuesNotBlankUseCase: CheckValuesNotBlankUseCase,
    val fetchLocationsUseCase: FetchLocationsUseCase,
    val createUserUseCase: CreateUserUseCase
)
