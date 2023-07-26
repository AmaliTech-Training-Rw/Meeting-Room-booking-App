package com.amalitech.user.state

import com.amalitech.user.models.User

data class UserViewState(
    val loading: Boolean = true,
    val users: List<User> = emptyList()
)
