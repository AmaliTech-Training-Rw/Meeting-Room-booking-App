package com.amalitech.user.models

data class User(
    val userId: String,
    val profilePic: String,
    val username: String,
    val email: String,
    val isActive: Boolean
)
