package com.amalitech.bookmeetingroom.authentication_presentation.forgot_password

sealed class ForgotPasswordEvent {
    data class OnNewEmail(val email: String): ForgotPasswordEvent()
    object OnSendResetLink: ForgotPasswordEvent()
}
