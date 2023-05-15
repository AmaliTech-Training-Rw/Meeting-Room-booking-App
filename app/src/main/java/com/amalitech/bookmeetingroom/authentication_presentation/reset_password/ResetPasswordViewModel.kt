package com.amalitech.bookmeetingroom.authentication_presentation.reset_password

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amalitech.bookmeetingroom.R
import com.amalitech.bookmeetingroom.authentication_domain.use_case.AuthenticationUseCase
import com.amalitech.bookmeetingroom.util.UiEvents
import com.amalitech.bookmeetingroom.util.UiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ResetPasswordViewModel(
    private val authenticationUseCase: AuthenticationUseCase
): ViewModel() {

    var state by mutableStateOf(
        ResetPasswordState()
    )
        private set

    private val _uiEvent = Channel<UiEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: ResetPasswordEvent) {
        viewModelScope.launch {
            when (event) {
                is ResetPasswordEvent.OnNewPassword -> {
                    state = state.copy(
                        newPassword = event.password.trim()
                    )
                }

                is ResetPasswordEvent.OnConfirmNewPassword -> {
                    state = state.copy(
                        confirmNewPassword = event.password.trim()
                    )
                    state = state.copy(
                        error = authenticationUseCase.checkPasswordsMatch(state.newPassword, state.confirmNewPassword)
                    )
                }

                ResetPasswordEvent.OnSaveChangesClick -> {
                    state = state.copy(
                        error = authenticationUseCase.checkPasswordsMatch(state.newPassword, state.confirmNewPassword)
                    )
                    if (state.error == null) {
                        val result = authenticationUseCase.resetPassword(
                            state.newPassword,
                            state.confirmNewPassword
                        )
                        if (result != null) {
                            state = state.copy(
                                error = result
                            )
                        } else {
                            _uiEvent.send(
                                UiEvents.showSnackBar(
                                    UiText.StringResource(R.string.password_reset_successfully)
                                )
                            )
                            _uiEvent.send(
                                UiEvents.NavigateToHome
                            )
                        }
                    }
                }
            }
        }
    }
}
