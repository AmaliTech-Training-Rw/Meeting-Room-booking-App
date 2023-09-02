package com.amalitech.onboarding.login.model

data class UserProfile(
    val createdAt: String,
    val email: String,
    val firstName: String,
    val id: Int,
    val isAdmin: Int,
    val lastLogin: String,
    val lastName: String,
    val locationId: Int,
    val organisationId: Int,
    val updatedAt: String,
    val userId: Int,
    val username: String,
    val title: String = "",
    val profileImgUrl: String = "",
    val token: String = ""
)
