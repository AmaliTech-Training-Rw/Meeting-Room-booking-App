package com.amalitech.user.profile.model

import android.net.Uri

data class Profile(
    val firstName: String,
    val lastName: String,
    val email: String,
    val title: String,
    val profileImage: Uri? = null,
    val newPassword: String,
    val oldPassword: String,
)
