package com.amalitech.user.adduser

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amalitech.core.util.UiText
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
    private val _snackBarMessage: MutableState<UiText?> = mutableStateOf(null)
    val snackbarMessage: State<UiText?> = _snackBarMessage

    init {
        fetchUsersFromDb()
    }

    private fun fetchUsersFromDb() {
        TODO("Not yet implemented")
    }

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
                selectLocation = location
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
               _snackBarMessage.value = UiText.StringResource(R.string.first_name_empty)
               return
           }

           _userUiState.value.lastName.isBlank() -> {
               _snackBarMessage.value = UiText.StringResource(R.string.last_name_empty)
               return
           }

           _userUiState.value.email.isBlank() -> {
               _snackBarMessage.value = UiText.StringResource(R.string.email_empty)
               return
           }

           _userUiState.value.selectLocation.isBlank() -> {
               _snackBarMessage.value = UiText.StringResource(R.string.location_selection_empty)
               return
           }
       }

        launchCatching {
            // addUserUseCase( )
        }
        _snackBarMessage.value = UiText.DynamicString("User invited successfully")
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

    fun clearSnackBar() {
        _snackBarMessage.value = null
    }
}