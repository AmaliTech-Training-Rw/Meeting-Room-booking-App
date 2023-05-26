package com.amalitech.onboarding_domain.login.use_case

data class LoginUseCase(
    val logIn: com.amalitech.onboarding_domain.login.use_case.LogIn,
    val validateEmail: com.amalitech.onboarding_domain.login.use_case.ValidateEmail,
    val validatePassword: com.amalitech.onboarding_domain.login.use_case.ValidatePassword
)
