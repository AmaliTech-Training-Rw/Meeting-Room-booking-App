package com.amalitech.user.adduser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amalitech.core_ui.util.SnackbarManager
import com.amalitech.core_ui.util.SnackbarMessage.Companion.toSnackbarMessage
import com.amalitech.ui.user.R
import com.amalitech.user.models.User
import com.amalitech.user.state.UserUiState
import com.amalitech.user.usecases.AddUserUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddUserViewModel(
    private val addUserUseCase: AddUserUseCase
): ViewModel() {

    private val _userUiState = MutableStateFlow(
        UserUiState()
    )
    val userUiState = _userUiState.asStateFlow()

    fun onFirstName(name: String) {
        _userUiState.update { firstName ->
            firstName.copy(
                firstName = name
            )
        }
    }

    fun onLastName(name: String) {
        _userUiState.update { lastName ->
            lastName.copy(
                lastName = name
            )
        }
    }

    fun onEmailName(email: String) {
        _userUiState.update { mail ->
            mail.copy(
                email = email
            )
        }
    }

    fun onLocationName(location: String) {
        _userUiState.update { loc ->
            loc.copy(
                email = location
            )
        }
    }

    fun onIsAdminChecked(isAdmin: Boolean) {
        _userUiState.update { loc ->
            loc.copy(
                isAdmin = isAdmin
            )
        }
    }

    fun invite() {
       when {
           _userUiState.value.firstName.isBlank() -> {
               SnackbarManager.showMessage(R.string.first_name_empty)
               return
           }

           _userUiState.value.lastName.isBlank() -> {
               SnackbarManager.showMessage(R.string.last_name_empty)
               return
           }

           _userUiState.value.email.isBlank() -> {
               SnackbarManager.showMessage(R.string.email_empty)
               return
           }

           _userUiState.value.selectLocation.isBlank() -> {
               SnackbarManager.showMessage(R.string.location_selection_empty)
               return
           }
       }

        launchCatching {
            addUserUseCase(
                mapUserToDomain(
                    _userUiState.value.firstName,
                    _userUiState.value.lastName,
                    _userUiState.value.email,
                    _userUiState.value.selectLocation,
                    _userUiState.value.isAdmin
                )
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

    // TODO: crate a share mapping logic/function
    private fun mapUserToDomain(
        firstName: String,
        lastName: String,
        email: String,
        location: String,
        isAdmin: Boolean
    ): User {
        return User(
            firstName,
            lastName,
            email,
            location,
            isAdmin
        )
    }
}