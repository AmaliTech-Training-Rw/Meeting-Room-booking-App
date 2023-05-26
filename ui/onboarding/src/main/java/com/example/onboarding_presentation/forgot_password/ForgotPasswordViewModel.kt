package com.example.onboarding_presentation.forgot_password

import androidx.lifecycle.ViewModel
import com.amalitech.core.util.UiText
import com.amalitech.ui.onboarding.R
import com.example.oboarding_domain.forgot_password.use_case.ForgotPasswordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ForgotPasswordViewModel(
    private val forgotPasswordUseCase: ForgotPasswordUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(ForgotPasswordUiState())
    val uiState = _uiState.asStateFlow()

    /**
     * onNewEmail - trims and adds value of email entered by the user in our state
     *
     * @param email the email address entered by the user
     */
    fun onNewEmail(email: String) {
        _uiState.update { forgotPasswordUiState ->
            forgotPasswordUiState.copy(
                email = email.trim()
            )
        }
    }

    /**
     * onSendResetLink - Validates given email address via use cases and send a reset link
     *
     * if any use case returns
     * an instance of UiText, there is a message/error that need
     * to be displayed for the user. Otherwise, everything worked
     * fine
     */
    fun onSendResetLink() {
        _uiState.update { forgotPasswordUiState ->
            forgotPasswordUiState.copy(
                error = forgotPasswordUseCase.validateEmail(_uiState.value.email)
            )
        }
        if (_uiState.value.error == null) {
            val result = forgotPasswordUseCase.sendResetLink(_uiState.value.email)
            if (result == null) {
                _uiState.update { forgotPasswordUiState ->
                    forgotPasswordUiState.copy(
                        snackBarValue =  UiText.StringResource(R.string.link_sent_inbox),
                        linkSent = true
                    )
                }
            } else {
                _uiState.update { forgotPasswordUiState ->
                    forgotPasswordUiState.copy(
                        error =  result
                    )
                }
            }
        }
    }

    /**
     * onSnackBarShown - Reset snackBarValue to null
     */
    fun onSnackBarShown() {
        _uiState.update { forgotPasswordUiState ->
            forgotPasswordUiState.copy(
                snackBarValue = null
            )
        }
    }

}
