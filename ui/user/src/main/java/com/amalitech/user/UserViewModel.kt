package com.amalitech.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amalitech.core.util.UiText
import com.amalitech.core_ui.util.SnackbarManager
import com.amalitech.core_ui.util.SnackbarMessage.Companion.toSnackbarMessage
import com.amalitech.user.models.User
import com.amalitech.user.state.UserViewState
import com.amalitech.user.usecases.GetUseCase
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
    private var usersCopy: List<User> = emptyList()

    init {
        subscribeToUserUpdates()
    }

    private fun subscribeToUserUpdates() {
        launchCatching {
            _uiState.value = uiState.value.copy(loading = true)
            getUsers().collect { user ->
                val updatedUserSet = (uiState.value.users + user).toSet() // remove dups
                _uiState.update { oldState ->
                    usersCopy = updatedUserSet.toList()
                    oldState.copy( loading = false, users = usersCopy)
                }
            }
        }
    }

    fun onDelete() {
        _uiState.update {
            it.copy(
                snackbarMessage = UiText.DynamicString("User deleted successfully")
            )
        }
    }

    fun onAddUser() {
        _uiState.update {
            it.copy(
                snackbarMessage = UiText.DynamicString("Works")
            )
        }
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

    fun clearMessage() {
        _uiState.update {
            it.copy(
                snackbarMessage = null
            )
        }
    }

    fun onNewSearchQuery(query: String) {
        _uiState.update {
            it.copy(searchQuery = query)
        }
        onSearch()
    }

    fun onSearch() {
        _uiState.update { state ->
            state.copy(
                users = usersCopy.filter { user ->
                    user.username.contains(state.searchQuery, true) || user.email.contains(
                        state.searchQuery,
                        true
                    )
                }
            )
        }
    }

    fun resetList() {
        _uiState.update {
            it.copy(
                users = usersCopy,
                searchQuery = ""
            )
        }
    }
}
