package com.amalitech.bookmeetingroom.authentication_presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amalitech.bookmeetingroom.authentication_domain.use_case.UseCase
import com.amalitech.bookmeetingroom.util.UiEvents
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val useCase: UseCase): ViewModel() {
    var state by mutableStateOf(
        LoginState()
    )
        private set

    private val _uiEvent = Channel<UiEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()

    /**
     * onEvent - Manages all events coming from the login screen
     * by sending them to the use case. If the use case returns
     * an instance of UiText, there is a message/error that need
     * to be displayed for the user. Otherwise, everything worked
     * fine.
     * @param event an instance of LoginEvent
     */
    fun onEvent(event: LoginEvent) {
        viewModelScope.launch {
            when (event) {
                is LoginEvent.OnNewEmail -> {
                    state = state.copy(
                        email = event.email.trim()
                    )
                }

                is LoginEvent.OnNewPassword -> {
                    state = state.copy(
                        password = event.password.trim()
                    )
                }

                LoginEvent.OnLoginClick -> {
                    val emailValidation = useCase.validateEmail(state.email)
                    val passwordValidation = useCase.validatePassword(state.password)
                    if (emailValidation == null && passwordValidation == null) {
                        val result = useCase.logIn(email = state.email, password = state.password)
                        if (result != null) {
                            state = state.copy(
                                error = result
                            )
                        } else {
                            _uiEvent.send(
                                UiEvents.NavigateToHome
                            )
                        }
                    } else if (emailValidation != null) {
                        state = state.copy(
                            error = emailValidation
                        )
                    } else {
                        state = state.copy(
                            error = passwordValidation
                        )
                    }
                }
            }
        }
    }
}