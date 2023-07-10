package com.amalitech.onboarding.login.use_case

import com.amalitech.core.domain.use_case.ValidateEmail

data class LoginUseCase(
    val logIn: LogIn,
    val validateEmail: ValidateEmail,
    val validatePassword: ValidatePassword,
    val isUserAdmin: IsUserAdmin
)
