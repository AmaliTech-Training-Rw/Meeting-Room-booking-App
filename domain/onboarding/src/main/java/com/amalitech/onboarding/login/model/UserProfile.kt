package com.amalitech.onboarding.login.model

data class UserProfile(
    val email: String,
    val firstName: String,
    val id: Int,
    val isAdmin: Int,
    val lastName: String,
    val locationId: Int,
    val organisationId: Int,
    val userId: Int,
    val username: String,
    val title: String = "",
    val profileImgUrl: String = "",
    val token: String = ""
)
