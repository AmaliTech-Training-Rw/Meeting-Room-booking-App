package com.amalitech.bookmeetingroom.authentication_presentation

sealed class LoginEvent {
    data class OnNewEmail(val email: String): LoginEvent()
    data class OnNewPassword(val password: String): LoginEvent()
    object OnLoginClick: LoginEvent()
}
