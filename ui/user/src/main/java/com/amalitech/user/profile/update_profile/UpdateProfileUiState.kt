package com.amalitech.user.profile.update_profile

import com.amalitech.core.util.UiText

data class UpdateProfileUiState(
    val profileUserInput: ProfileUserInput = ProfileUserInput(),
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val canNavigate: Boolean = false
)
