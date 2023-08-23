package com.amalitech.user.state

import com.amalitech.core.util.UiText
import com.amalitech.user.models.User

data class UserViewState(
    val loading: Boolean = true,
    val users: List<User> = emptyList(),
    val snackbarMessage: UiText? = null,
    val searchQuery: String = ""
)

data class UserUiState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val selectLocation: String = "",
    val isAdmin: Boolean = false,
)
