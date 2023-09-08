package com.amalitech.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amalitech.core.util.UiText
import com.amalitech.user.models.User
import com.amalitech.user.state.UserViewState
import com.amalitech.user.usecases.GetUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserViewModel(
    private val getUsers: GetUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        UserViewState()
    )
    val uiState = _uiState.asStateFlow()
    private var usersCopy: StateFlow<List<User>> = MutableStateFlow(emptyList())

    init {
        subscribeToUserUpdates()
    }

    private fun subscribeToUserUpdates() {
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(loading = true)
            val apiResult = getUsers(_uiState.value.isInviting)

            apiResult.data?.let { listFlow ->
                val stateFlow = listFlow.stateIn(viewModelScope)
                _uiState.update {
                    usersCopy = stateFlow
                    it.copy(
                        users = stateFlow, loading = false
                    )
                }
            }
            apiResult.error?.let { error ->
                _uiState.update {
                    it.copy(
                        loading = false, snackbarMessage = error
                    )
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
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    users = flowOf(usersCopy.value.filter { user ->
                        user.username.contains(state.searchQuery, true) || user.email.contains(
                            state.searchQuery, true
                        )
                    }).stateIn(viewModelScope)
                )
            }
        }
    }

    fun resetList() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    users = usersCopy, searchQuery = ""
                )
            }
        }
    }

    fun onSnackBarShown() {
        _uiState.update {
            it.copy(
                snackbarMessage = null
            )
        }
    }

    fun isInviting(value: Boolean) {
        _uiState.update {
            it.copy(isInviting = value)
        }
    }
}
