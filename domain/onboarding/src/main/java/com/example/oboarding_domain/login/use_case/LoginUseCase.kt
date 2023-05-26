package com.example.oboarding_domain.login.use_case

data class LoginUseCase(
    val logIn: LogIn,
    val validateEmail: ValidateEmail,
    val validatePassword: ValidatePassword
)
