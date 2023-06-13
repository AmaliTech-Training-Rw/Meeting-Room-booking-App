package com.amalitech.onboarding.reset_password

import androidx.lifecycle.viewModelScope
import com.amalitech.core_ui.util.AuthenticationBaseViewModel
import com.amalitech.core_ui.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ResetPasswordViewModel(
    private val resetPasswordUseCase: ResetPasswordUseCase
) : AuthenticationBaseViewModel<ResetPasswordUiState>() {

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
        val passwordCheck = resetPasswordUseCase.checkPasswordsMatch(
            _uiState.value.newPassword,
            _uiState.value.passwordConfirmation
        )
        if (passwordCheck != null) {
            baseResult.update {
                UiState.Error(
                    error = passwordCheck
                )
            }
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
            baseResult.update {
                UiState.Loading()
            }
            val passwordsCheck = resetPasswordUseCase.checkPasswordsMatch(
                _uiState.value.newPassword,
                _uiState.value.passwordConfirmation)

            if (passwordsCheck == null) {
                val apiResult = resetPasswordUseCase.resetPassword(
                    _uiState.value.newPassword,
                    _uiState.value.passwordConfirmation
                )

                if (apiResult != null) {
                    baseResult.update {
                        UiState.Error(
                            error = apiResult
                        )
                    }
                } else {
                    baseResult.update {
                        UiState.Success()
                    }
                }
            } else {
                baseResult.update {
                    UiState.Error(error = passwordsCheck)
                }
            }
        }
    }
}
