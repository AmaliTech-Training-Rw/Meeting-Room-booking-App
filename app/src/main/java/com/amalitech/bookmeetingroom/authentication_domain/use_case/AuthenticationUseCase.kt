package com.amalitech.bookmeetingroom.authentication_domain.use_case

data class UseCase(
    val validateEmail: ValidateEmail,
    val logIn: LogIn,
    val validatePassword: ValidatePassword
)
