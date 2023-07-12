package com.amalitech.onboarding.forgot_password

import androidx.lifecycle.viewModelScope
import com.amalitech.core_ui.util.UiState
import com.amalitech.core_ui.util.BaseViewModel
import com.amalitech.onboarding.forgot_password.use_case.ForgotPasswordUseCasesWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(
    private val forgotPasswordUseCasesWrapper: ForgotPasswordUseCasesWrapper
) : BaseViewModel<ForgotPasswordUiState>() {
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
        if (job?.isActive == true)
            return
        job = viewModelScope.launch {
            _uiStateFlow.update {
                UiState.Loading()
            }
            val emailValidation = forgotPasswordUseCasesWrapper.validateEmailUseCase(_uiState.value.email)

            if (emailValidation == null) {
                val apiResult = forgotPasswordUseCasesWrapper.sendResetLinkUseCase(_uiState.value.email)
                if (apiResult == null) {
                    _uiStateFlow.update {
                        UiState.Success()
                    }
                } else {
                    _uiStateFlow.update {
                        UiState.Error(
                            error = apiResult
                        )
                    }
                }
            } else {
                _uiStateFlow.update {
                    UiState.Error(
                        error = emailValidation
                    )
                }
            }
        }
    }
}
