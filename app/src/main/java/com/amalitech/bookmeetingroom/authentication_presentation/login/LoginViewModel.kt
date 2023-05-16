package com.amalitech.bookmeetingroom.authentication_presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amalitech.bookmeetingroom.R
import com.amalitech.bookmeetingroom.authentication_domain.use_case.AuthenticationUseCase
import com.amalitech.bookmeetingroom.util.UiEvents
import com.amalitech.bookmeetingroom.util.UiText
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authenticationUseCase: AuthenticationUseCase,
    private val dispatchers: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {
    var state by mutableStateOf(
        LoginState()
    )
        private set

    private val _uiEvent = Channel<UiEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()

    /**
     * onEvent - Manages all events coming from the login screen
     * by sending them to the use case.
     *
     * If the use case returns
     * an instance of UiText, there is a message/error that need
     * to be displayed for the user. Otherwise, everything worked
     * fine.
     * @param event an instance of LoginEvent
     */
    fun onEvent(event: LoginEvent) {
        viewModelScope.launch(dispatchers) {
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
                    val emailValidation = authenticationUseCase.validateEmail(state.email)
                    val passwordValidation = authenticationUseCase.validatePassword(state.password)
                    if (emailValidation == null && passwordValidation == null) {
                        val result = authenticationUseCase.logIn(
                            email = state.email,
                            password = state.password
                        )
                        if (result != null) {
                            state = state.copy(
                                error = result
                            )
                        } else {
                            _uiEvent.send(UiEvents.ShowSnackBar(
                                UiText.StringResource(R.string.logged_in_successfully)
                            ))
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