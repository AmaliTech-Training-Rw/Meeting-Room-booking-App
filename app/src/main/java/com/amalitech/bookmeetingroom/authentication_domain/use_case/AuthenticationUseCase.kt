package com.amalitech.bookmeetingroom.authentication_domain.use_case

import com.amalitech.bookmeetingroom.authentication_presentation.reset_password.ResetPasswordEvent

data class AuthenticationUseCase(
    val validateEmail: ValidateEmail,
    val logIn: LogIn,
    val validatePassword: ValidatePassword,
    val checkPasswordsMatch: CheckPasswordsMatch,
    val resetPassword: ResetPassword,
    val sendResetLink: SendResetLink,
)
