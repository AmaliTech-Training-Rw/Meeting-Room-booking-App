package com.amalitech.user.profile.model

import java.io.File

data class Profile(
    val firstName: String,
    val lastName: String,
    val email: String,
    val title: String,
    val profileImage: File?,
    val newPassword: String,
    val oldPassword: String
)
