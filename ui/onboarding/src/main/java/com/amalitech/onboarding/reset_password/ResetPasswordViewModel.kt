package com.amalitech.onboarding.reset_password

import androidx.lifecycle.ViewModel
import com.amalitech.core.util.UiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ResetPasswordViewModel(
    private val resetPasswordUseCase: ResetPasswordUseCase
) : ViewModel() {

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
                error = resetPasswordUseCase.checkPasswordsMatch(
                    _uiState.value.newPassword,
                    _uiState.value.passwordConfirmation
                )
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
        _uiState.update { resetPasswordUiState ->
            resetPasswordUiState.copy(
                error = resetPasswordUseCase.checkPasswordsMatch(
                    _uiState.value.newPassword,
                    _uiState.value.passwordConfirmation
                )
            )
        }
        if (_uiState.value.error == null) {
            val result = resetPasswordUseCase.resetPassword(
                _uiState.value.newPassword,
                _uiState.value.passwordConfirmation
            )

            if (result != null) {
                _uiState.update { resetPasswordUiState ->
                    resetPasswordUiState.copy(
                        error = result
                    )
                }
            } else {
                _uiState.update { resetPasswordUiState ->
                    resetPasswordUiState.copy(
                        snackbarValue = UiText.StringResource(com.amalitech.core.R.string.password_reset_successfully),
                        passwordReset = true
                    )
                }
            }
        }
    }

    /**
     * onSnackBarShown - Updates value of snackBar in uiState to null
     */
    fun onSnackBarShown() {
        _uiState.update { resetPasswordUiState ->
            resetPasswordUiState.copy(
                snackbarValue = null
            )
        }
    }
}
