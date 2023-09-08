package com.amalitech.user.adduser

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amalitech.core.domain.use_case.FetchLocationsUseCase
import com.amalitech.core.util.UiText
import com.amalitech.ui.user.R
import com.amalitech.user.models.UserToAdd
import com.amalitech.user.state.UserUiState
import com.amalitech.user.usecases.AddUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddUserViewModel(
    private val addUserUseCase: AddUserUseCase,
    private val fetchLocationsUseCase: FetchLocationsUseCase
) : ViewModel() {

    private val _userUiState = MutableStateFlow(
        UserUiState()
    )

    val userUiState = _userUiState.asStateFlow()
    private val _snackBarMessage: MutableState<UiText?> = mutableStateOf(null)
    val snackbarMessage: State<UiText?> = _snackBarMessage

    init {
        fetchLocations()
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

    fun onIsAdminChecked(isAdmin: Boolean) {
        _userUiState.update { loc ->
            loc.copy(
                isAdmin = isAdmin
            )
        }
    }

    fun invite() {
        viewModelScope.launch {
            when {
                _userUiState.value.firstName.isBlank() -> {
                    _snackBarMessage.value = UiText.StringResource(R.string.first_name_empty)
                    return@launch
                }

                _userUiState.value.lastName.isBlank() -> {
                    _snackBarMessage.value = UiText.StringResource(R.string.last_name_empty)
                    return@launch
                }

                _userUiState.value.email.isBlank() -> {
                    _snackBarMessage.value = UiText.StringResource(R.string.email_empty)
                    return@launch
                }

                _userUiState.value.selectLocation == -1 -> {
                    _snackBarMessage.value =
                        UiText.StringResource(R.string.location_selection_empty)
                    return@launch
                }
            }

            val error = addUserUseCase(
                UserToAdd(
                    _userUiState.value.firstName,
                    _userUiState.value.lastName,
                    _userUiState.value.email,
                    _userUiState.value.selectLocation
                )
            )
            error?.let { errorValue ->
                _snackBarMessage.value = errorValue
            }
        }
    }

    fun clearSnackBar() {
        _snackBarMessage.value = null
    }

    fun onSelectedLocation(location: String) {
        val selectedId = _userUiState.value.locations.find {
            it.name == location
        }?.id ?: -1
        _userUiState.update {
            it.copy(
                selectLocation = selectedId
            )
        }
    }

    private fun fetchLocations() {
        viewModelScope.launch {
            _userUiState.update {
                it.copy(isLoading = true)
            }
            val apiResult = fetchLocationsUseCase()
            val data = apiResult.data
            val error = apiResult.error
            data?.let { locations ->
                _userUiState.update {
                    it.copy(
                        locations = locations,
                        isLoading = false
                    )
                }
            }
            error?.let { uiText ->
                _userUiState.update {
                    _snackBarMessage.value = uiText
                    it.copy(
                        isLoading = false,
                    )
                }
            }
        }
    }
}
