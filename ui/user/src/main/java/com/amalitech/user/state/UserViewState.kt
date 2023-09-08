package com.amalitech.user.state

import com.amalitech.core.domain.model.LocationX
import com.amalitech.core.util.UiText
import com.amalitech.user.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class UserViewState(
    val loading: Boolean = true,
    val users: StateFlow<List<User>> = MutableStateFlow(emptyList()),
    val snackbarMessage: UiText? = null,
    val searchQuery: String = "",
    val isInviting: Boolean = false
)

data class UserUiState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val selectLocation: Int = -1,
    val isAdmin: Boolean = false,
    val locations: List<LocationX> = emptyList(),
    val isLoading: Boolean = false,
    val isInviting: Boolean = false
)
