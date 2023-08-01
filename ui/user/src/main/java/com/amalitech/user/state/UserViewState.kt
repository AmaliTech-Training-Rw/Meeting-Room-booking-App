package com.amalitech.user.state

import android.net.Uri
import com.amalitech.user.models.User

data class UserViewState(
    val loading: Boolean = true,
    val users: List<User> = emptyList()
)

data class UserUiState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val selectLocation: String = "",
    val isAdmin: Boolean = false,
)
