package com.amalitech.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amalitech.core_ui.util.SnackbarManager
import com.amalitech.core_ui.util.SnackbarMessage.Companion.toSnackbarMessage
import com.amalitech.user.models.User
import com.amalitech.user.state.UserUiState
import com.amalitech.user.usecases.GetUseCase
import com.amalitech.user.state.UserViewState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserViewModel (
    private val getUsers: GetUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(
        UserViewState()
    )
    val uiState = _uiState.asStateFlow()

    init {
        subscribeToUserUpdates()
    }

    private fun subscribeToUserUpdates() {
        launchCatching {
            _uiState.value = uiState.value.copy(loading = true)
            getUsers().collect { user ->
                val updatedUserSet = (uiState.value.users + user).toSet()
                _uiState.update { oldState ->
                    oldState.copy( loading = false, users = updatedUserSet.toList())
                }
            }
        }
    }

    fun onAddUser() {

    }

    // TODO: ideally, this method should come from a share vm
    fun launchCatching(snackbar: Boolean = true, block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                if (snackbar) {
                    SnackbarManager.showMessage(throwable.toSnackbarMessage())
                }
            },
            block = block
        )
}
