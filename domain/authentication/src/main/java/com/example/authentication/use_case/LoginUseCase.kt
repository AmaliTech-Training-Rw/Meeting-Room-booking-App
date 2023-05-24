package com.example.authentication.use_case

data class LoginUseCase(
    val logIn: LogIn,
    val validateEmail: ValidateEmail,
    val validatePassword: ValidatePassword
)
