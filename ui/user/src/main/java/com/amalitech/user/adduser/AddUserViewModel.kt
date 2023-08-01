package com.amalitech.user.adduser

import androidx.lifecycle.ViewModel
import com.amalitech.user.state.UserUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AddUserViewModel (): ViewModel() {

    private val _userUiState = MutableStateFlow(
        UserUiState()
    )
    val userUiState = _userUiState.asStateFlow()

    private val first_name
        get() = _userUiState.value.firstName

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
}