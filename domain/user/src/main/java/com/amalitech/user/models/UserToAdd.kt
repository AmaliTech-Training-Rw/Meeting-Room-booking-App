package com.amalitech.user.models

data class UserToAdd(
    val firstName: String,
    val lastName: String,
    val email: String,
    val locationId: Int,
    val isAdmin: Boolean
)
