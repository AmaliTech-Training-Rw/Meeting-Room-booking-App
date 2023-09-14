package com.amalitech.onboarding.reset_password

import androidx.lifecycle.viewModelScope
import com.amalitech.core_ui.util.BaseViewModel
import com.amalitech.core_ui.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ResetPasswordViewModel(
    private val resetPasswordUseCasesWrapper: ResetPasswordUseCasesWrapper
) : BaseViewModel<ResetPasswordUiState>() {

    private val _uiState = MutableStateFlow(
        ResetPasswordUiState()
    )
    val uiState = _uiState.asStateFlow()

    /**
     * onNewPassword - trims and adds value of password entered by the user in our state
     *
     * @param password the password entered by the user
     */

    fun onNewPassword(password: String) {
        _uiState.update { resetPasswordUiState ->
            resetPasswordUiState.copy(
                newPassword = password.trim()
            )
        }
    }


    /**
     * onNewToken - adds value of token gotten from navigation
     *
     * @param token the token gotten from the sent link
     */

    fun onNewToken(token: String) {
        _uiState.update { resetPasswordUiState ->
            resetPasswordUiState.copy(
                token = token
            )
        }
    }

    /**
     * onNewPasswordConfirmation - trims and adds value of password confirmation entered by the user in our state
     * then checks if the two passwords match
     *
     * @param password the password confirmation entered by the user
     */

    fun onNewPasswordConfirmation(password: String) {
        _uiState.update { resetPasswordUiState ->
            resetPasswordUiState.copy(
                passwordConfirmation = password.trim(),
            )
        }
    }

    /**
     * onResetPassword - Checks if passwords entered by the user match and
     * reset them if they match.
     *
     * If they don't match, it sends an error to the user.
     *
     * If they match, and they have been reset, it updates the value of snackbar
     * so that it can be shown to the user, and it also updates the value of
     * passwordReset for the ui to handle it
     */
    fun onResetPassword() {
        if (job?.isActive == true)
            return
        job = viewModelScope.launch {
            _uiStateFlow.update {
                UiState.Loading()
            }
            val passwordValid = resetPasswordUseCasesWrapper.validatePasswordUseCase(_uiState.value.newPassword)
            val passwordsCheck = resetPasswordUseCasesWrapper.checkPasswordsMatchUseCase(
                _uiState.value.newPassword,
                _uiState.value.passwordConfirmation)

            if (passwordsCheck == null && passwordValid == null) {
                val apiResult = resetPasswordUseCasesWrapper.resetPasswordUseCase(
                    newPassword = _uiState.value.newPassword,
                    newPasswordConfirmation = _uiState.value.passwordConfirmation,
                    token = _uiState.value.token
                )

                if (apiResult != null) {
                    _uiStateFlow.update {
                        UiState.Error(
                            error = apiResult
                        )
                    }
                } else {
                    _uiStateFlow.update {
                        UiState.Success()
                    }
                }
            } else if (passwordsCheck != null) {
                _uiStateFlow.update {
                    UiState.Error(error = passwordsCheck)
                }
            } else {
                _uiStateFlow.update {
                    UiState.Error(error = passwordValid)
                }
            }
        }
    }
}
