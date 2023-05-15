package com.amalitech.bookmeetingroom.authentication_presentation.reset_password

sealed class ResetPasswordEvent {
    data class OnNewPassword(val password: String): ResetPasswordEvent()
    data class OnConfirmNewPassword(val password: String): ResetPasswordEvent()
    object OnSaveChangesClick: ResetPasswordEvent()
}
