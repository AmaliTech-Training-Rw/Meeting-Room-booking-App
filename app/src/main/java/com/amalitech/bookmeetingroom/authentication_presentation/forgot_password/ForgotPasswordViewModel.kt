package com.amalitech.bookmeetingroom.authentication_presentation.forgot_password

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

class ForgotPasswordViewModel(
    private val authenticationUseCase: AuthenticationUseCase
): ViewModel() {
    var state by mutableStateOf(
        ForgotPasswordState()
    )
        private set

    private val _uiEvent = Channel<UiEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()

    /**
     * onEvent - Manages all events coming from the forgot password screen
     * by sending them to the use case.
     *
     * If the use case returns
     * an instance of UiText, there is a message/error that need
     * to be displayed for the user. Otherwise, everything worked
     * fine.
     * @param event an instance of LoginEvent
     */
    fun onEvent(event: ForgotPasswordEvent) {
        viewModelScope.launch {
            when(event) {
                is ForgotPasswordEvent.OnNewEmail -> {
                    state = state.copy(
                        email = event.email
                    )
                }

                ForgotPasswordEvent.OnSendResetLink -> {
                    state = state.copy(
                        error = authenticationUseCase.validateEmail(state.email)
                    )
                    if (state.error == null) {
                        val result = authenticationUseCase.sendResetLink(state.email)
                        if (result == null) {
                            _uiEvent.send(
                                UiEvents.showSnackBar(
                                    text = UiText.StringResource(R.string.link_sent_inbox)
                                )
                            )
                            _uiEvent.send(
                                UiEvents.NavigateToHome
                            )
                        } else {
                            state = state.copy(
                                error = result
                            )
                        }
                    }
                }
            }
        }
    }

}
