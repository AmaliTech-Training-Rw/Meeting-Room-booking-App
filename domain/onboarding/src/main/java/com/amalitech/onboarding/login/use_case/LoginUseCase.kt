package com.amalitech.onboarding.login.use_case

data class LoginUseCase(
    val logIn: LogIn,
    val validateEmail: ValidateEmail,
    val validatePassword: ValidatePassword,
    val isUserAdmin: IsUserAdmin
)
