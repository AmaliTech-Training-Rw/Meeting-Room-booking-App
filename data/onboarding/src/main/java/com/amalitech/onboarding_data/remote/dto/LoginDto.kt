package com.amalitech.onboarding_data.remote.dto

import com.amalitech.core.data.dto.UserData

data class LoginDto(
    val `data`: UserData?,
    val status: Boolean?,
    val successLoginMessage: String?,
    val token: String?
)