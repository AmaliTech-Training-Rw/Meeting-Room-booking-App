package com.amalitech.user.profile

import com.amalitech.user.profile.model.dto.UserDto

data class ProfileUiState(
    val user: UserDto,
    val isAdmin: Boolean,
    val isUsingAdminDashboard: Boolean
)
