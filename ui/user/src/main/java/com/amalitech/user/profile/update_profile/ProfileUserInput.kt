package com.amalitech.user.profile.update_profile

import android.net.Uri
import com.amalitech.user.profile.model.Profile

data class ProfileUserInput(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val title: String = "",
    val profileImage: Uri? = null,
    val newPassword: String = "",
    val oldPassword: String = "",
    val newPasswordConfirmation: String = "",
    val profileImageUrl: String = ""
) {
    fun toProfile(): Profile {
        return Profile(
            firstName = firstName,
            lastName = lastName,
            email = email,
            title = title,
            profileImage = profileImage,
            newPassword = newPassword,
            oldPassword = oldPassword,
        )
    }
}
