package com.amalitech.onboarding_data.remote.dto

data class LoginDto(
    val `data`: LoginData?,
    val status: Boolean?,
    val successLoginMessage: String?,
    val token: String?
)