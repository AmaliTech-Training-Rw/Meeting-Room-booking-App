package com.amalitech.onboarding.signup.use_case

import com.amalitech.onboarding.login.use_case.ValidateEmail
import com.amalitech.onboarding.login.use_case.ValidatePassword
import com.amalitech.onboarding.reset_password.CheckPasswordsMatch

data class SignupUseCase(
    val isEmailAvailable: IsEmailAvailable,
    val isUsernameAvailable: IsUsernameAvailable,
    val fetchOrganizationsType: FetchOrganizationsType,
    val signup: Signup,
    val validatePassword: ValidatePassword,
    val validateEmail: ValidateEmail,
    val checkPasswordsMatch: CheckPasswordsMatch,
    val checkValuesNotBlank: CheckValuesNotBlank,
)
