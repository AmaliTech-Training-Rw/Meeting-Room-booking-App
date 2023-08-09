package com.amalitech.onboarding.login.model

data class UserProfile(
    val email: String,
    val firstName: String,
    val lastName: String,
    val title: String,
    val profileImgUrl: String
)
