package com.amalitech.bookmeetingroom.authentication_presentation.reset_password

import com.amalitech.bookmeetingroom.util.UiText

data class ResetPasswordState(
    val newPassword: String = "",
    val confirmNewPassword: String = "",
    val error: UiText? = null
)
