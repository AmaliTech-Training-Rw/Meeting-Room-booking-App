package com.amalitech.bookmeetingroom.login_domain.use_case

data class UseCase(
    val validateEmail: ValidateEmail,
    val logIn: LogIn,
    val validatePassword: ValidatePassword
)
