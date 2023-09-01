package com.amalitech.user.models

data class User(
    val userId: Int,
    val profilePic: String,
    val userName: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val isActive: Boolean
)
