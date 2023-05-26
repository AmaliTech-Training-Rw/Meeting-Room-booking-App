package com.amalitech.onboarding.login.use_case

data class LoginUseCase(
    val logIn: com.amalitech.onboarding.login.use_case.LogIn,
    val validateEmail: com.amalitech.onboarding.login.use_case.ValidateEmail,
    val validatePassword: com.amalitech.onboarding.login.use_case.ValidatePassword
)
